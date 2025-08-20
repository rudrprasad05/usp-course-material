import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    Map<String, User> userList = new ConcurrentHashMap<String, User>();

    //Add a user
    public void addUser(String firstName, String lastName, String phoneNumber) {
        User user = new User(firstName, lastName, phoneNumber);
        validateUser(user);
        checkIfUserExists(user);
        userList.put(generateKey(user), user);
    }

    //Get User listings
    public Collection<User> getAllUsers() {
        return userList.values();
    }

    //Validate the users
    private void validateUser(User user) {
        user.validateFirstName();
        user.validateLastName();
        user.validatePhoneNumber();
    }

    //Check if user already exists hashmap
    private void checkIfUserExists(User user) {
        if (userList.containsKey(generateKey(user)))
            throw new RuntimeException("User already Exists!");
    }

    //Key generation function
    private String generateKey(User user) {
        return String.format("%s-%s", user.getFirstName(), user.getLastName());
    }

}
