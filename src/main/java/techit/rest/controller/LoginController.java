package techit.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import techit.model.User;
import techit.model.dao.UserDao;
import techit.rest.error.RestException;

public class LoginController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody User user) {
		User inputUser = userDao.getUser(user.getUsername());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(inputUser!=null)
		{
			if(passwordEncoder.matches(user.getPassword(), inputUser.getHash()))
			{
				String jws = Jwts.builder()
  					  .setSubject(inputUser.getUsername())
  	    			  .signWith(SignatureAlgorithm.HS512, "KEY")
  	    			  .compact();
						
				return jws;
			}
		}
		throw new RestException( 401, "Invalid credentials" );
	}
}
