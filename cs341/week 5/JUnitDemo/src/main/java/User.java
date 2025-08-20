public class User {

    //Private variables
    private String firstName;
    private String lastName;
    private String phoneNumber;

    //Constructor
    public User(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    //Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void validateFirstName() {
        if (this.firstName.isBlank())
            throw new RuntimeException("First Name cannot be null or empty!");
    }

    public void validateLastName() {
        if (this.lastName.isBlank())
            throw new RuntimeException("Last Name cannot be null or empty!");
    }

    public void validatePhoneNumber() {
        if (this.phoneNumber.isBlank())
            throw new RuntimeException("Phone Number cannot be null or empty!");

        if (this.phoneNumber.length() != 7)
            throw new RuntimeException("Phone number should be 7 digits long!");

        if (!this.phoneNumber.matches("\\d+"))
            throw new RuntimeException("Phone number should contain digits");

        if (!this.phoneNumber.startsWith("9"))
            throw new RuntimeException("Phone number should start with a 9!");
    }
}
