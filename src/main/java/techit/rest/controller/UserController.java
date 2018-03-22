package techit.rest.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import techit.model.User;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

@RestController
@ControllerAdvice(assignableTypes = {TicketController.class, UnitController.class,UserController.class})
public class UserController {
	
	@Autowired
    private UserDao userDao;

	@ModelAttribute("currentUser")
	public User getUserWithJwt(@RequestHeader(value = "Authorization",required = false) String token) {
		if(token == null)
			throw new RestException(401, "no token found");
		else
		{
		String[] tokens = token.split(" ");
		String userName = Jwts.parser().setSigningKey("KEY").parseClaimsJws(tokens[1]).getBody().getSubject();
		return userDao.getUser(userName);
		}
	}
	
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers()
    {
        return userDao.getUsers();
    }

    //Viewing a particular user
    @SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@ModelAttribute("currentUser") User currentUser,@PathVariable Long id )
    {
    	if(currentUser.getType().equals("ADMIN") || currentUser.getId() == id)
    		if(userDao.getUser( id ) != null)
    			return userDao.getUser( id );
    		else
    			throw new RestException(404,"The requested User not found in the database");
    	else
    		throw new RestException(403,"Not an admin and not the current user");
        

    }
    
    //Adding a particular user
    @SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public User addUser(@ModelAttribute("currentUser") User currentUser,@RequestBody User user) {
    	if(!currentUser.getType().equals("ADMIN"))
    		throw new RestException(401,"Not admin to add user");
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	user.setHash(passwordEncoder.encode(user.getPassword()));
    	return userDao.saveUser(user);
    }

}