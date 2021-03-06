package techit.model.dao;

import java.util.List;

import techit.model.User;

public interface UserDao {

    User getUser( Long id );

    User getUser( String username );

    List<User> getUsers();

    User saveUser( User user );

	void deleteUser(Long id);

}
