import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private static UserManager userManager;

    @BeforeAll
    public static void setupAll(){
        userManager = new UserManager();
        System.out.println("I should be printed before all the tests commence!");
    }

    @BeforeEach
    public void setup(){
        System.out.println("I am Instantiating the User Test Scripts!");
        //Helps initialise the UserObject
        userManager = new UserManager();
    }

    //Actual Test Cases
    @Test
    @DisplayName("Should not create user when first name is null.")
    public void shouldThrowRunTimeExceptionWhenFirstNameIsNull(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            userManager.addUser(null,"Prasad","9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when last name is null.")
    public void shouldThrowRunTimeExceptionWhenLastNameIsNull(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin",null,"9998083");
        });
    }

    @Test
    @DisplayName("Should not create user when phone number is null.")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            userManager.addUser("Shalvin", "Prasad", null);
        });
    }

    @Test
    @DisplayName("Should create user.")
    public void shouldCreateUser() {
        userManager.addUser("Shalvin","Prasad","9998083");
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @Test
    @DisplayName("Phone number should start with 9.")
    public void shouldTestPhoneNumberFormat() {
        userManager.addUser("Shalvin","Prasad","9998083");
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @Nested
    class RepeatedTests {
        @DisplayName("Repeat add user test 10 times.")
        @RepeatedTest(value = 10, name = "Repeating Test {currentRepetition} of {totalRepetitions}")
        public void shouldCreateUserMultipleTimes() {
            userManager.addUser("Shalvin", "Prasad", "9998083");
            assertFalse(userManager.getAllUsers().isEmpty());
            assertEquals(1, userManager.getAllUsers().size());
        }
    }

    @DisplayName("Add phones number from a value source.")
    @ParameterizedTest
    @ValueSource(strings = {"9998083","9998082","9998085"})
    public void shouldCreateUserUsingValueSource(String phoneNumber) {
        userManager.addUser("Shalvin","Prasad",phoneNumber);
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @DisplayName("Add phones number from a CSV source.")
    @ParameterizedTest
    @CsvSource({"9998083","9998082","9998085"})
    public void shouldCreateUserUsingCSVSource(String phoneNumber) {
        userManager.addUser("Shalvin","Prasad",phoneNumber);
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @DisplayName("Add phones number from a CSV File Source.")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldCreateUserUsingCSVFileSource(String phoneNumber) {
        userManager.addUser("Shalvin","Prasad",phoneNumber);
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    //Clean Up bits
    @AfterEach
    public void afterEachTest(){
        System.out.println("Just completed a test!");
    }

    @AfterAll
    public static void weAreDoneNow(){
        System.out.println("This is the last thing to be printed!");
    }

    //Disabled
    @Test
    @DisplayName("Test has been disabled!")
    @Disabled
    public void imNotWorking(){
        throw new RuntimeException("Test Should not be executed!");
    }

    @Test
    @DisplayName("We are going to Assume stuff here")
    public void shouldTestOnlyInDevEnvironment(){
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        userManager.addUser("Shalvin","Prasad","9998083");
        assertFalse(userManager.getAllUsers().isEmpty());
        assertEquals(1,userManager.getAllUsers().size());
    }

    @Test
    void testSetters() {
        User user = new User("John", "Doe", "9123456");

        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setPhoneNumber("9876543");

        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("9876543", user.getPhoneNumber());
    }

    @Test
    void testValidateFirstNameThrowsOnBlank() {
        User user = new User("", "Doe", "9123456");
        RuntimeException ex = assertThrows(RuntimeException.class, user::validateFirstName);
        assertEquals("First Name cannot be null or empty!", ex.getMessage());
    }

    @Test
    void testValidateFirstNameValid() {
        User user = new User("John", "Doe", "9123456");
        assertDoesNotThrow(user::validateFirstName);
    }

    @Test
    void testValidateLastNameThrowsOnBlank() {
        User user = new User("John", "", "9123456");
        RuntimeException ex = assertThrows(RuntimeException.class, user::validateLastName);
        assertEquals("Last Name cannot be null or empty!", ex.getMessage());
    }

    @Test
    void testValidateLastNameValid() {
        User user = new User("John", "Doe", "9123456");
        assertDoesNotThrow(user::validateLastName);
    }

    @Test
    void testValidatePhoneNumberThrowsOnBlank() {
        User user = new User("John", "Doe", "");
        RuntimeException ex = assertThrows(RuntimeException.class, user::validatePhoneNumber);
        assertEquals("Phone Number cannot be null or empty!", ex.getMessage());
    }

    @Test
    void testValidatePhoneNumberThrowsOnLength() {
        User user = new User("John", "Doe", "91234");
        RuntimeException ex = assertThrows(RuntimeException.class, user::validatePhoneNumber);
        assertEquals("Phone number should be 7 digits long!", ex.getMessage());
    }

    @Test
    void testValidatePhoneNumberThrowsOnNonDigits() {
        User user = new User("John", "Doe", "9123ABC");
        RuntimeException ex = assertThrows(RuntimeException.class, user::validatePhoneNumber);
        assertEquals("Phone number should contain digits", ex.getMessage());
    }

    @Test
    void testValidatePhoneNumberThrowsOnWrongStart() {
        User user = new User("John", "Doe", "8123456");
        RuntimeException ex = assertThrows(RuntimeException.class, user::validatePhoneNumber);
        assertEquals("Phone number should start with a 9!", ex.getMessage());
    }

    @Test
    void testValidatePhoneNumberValid() {
        User user = new User("John", "Doe", "9123456");
        assertDoesNotThrow(user::validatePhoneNumber);
    }

    @Test
    void testAddingDuplicateUserThrowsException() {
        // Add the first user
        userManager.addUser("John", "Doe", "9123456");

        // Try adding the same user again
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userManager.addUser("John", "Doe", "9123456")
        );

        assertEquals("User already Exists!", ex.getMessage());
    }
}