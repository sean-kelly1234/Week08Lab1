package businesslogic;

import dataaccess.UserDB;
import domainmodel.User;
import java.util.List;

public class UserService {

    private UserDB userDB;

    public UserService() {
        userDB = new UserDB();
    }

    public User get(String username) throws Exception {
        return userDB.getUser(username);
    }

    public List<User> getAll() throws Exception {
        return userDB.getAll();
    }

    public int update(String username, String password, String email, int active, String firstname, String lastname) throws Exception {
        User user = new User(username, password, email, active, firstname, lastname);
        return userDB.update(user);
    }

    public int delete(String username) throws Exception {
        User deletedUser = userDB.getUser(username);
        return userDB.delete(deletedUser);
    }

    public int insert(String username, String password, String email, int active, String firstname, String lastname) throws Exception {
        User user = new User(username, password, email, active, firstname, lastname);
        return userDB.insert(user);
    }
}
