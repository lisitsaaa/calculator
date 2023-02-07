package validator;

public class UserValidator {
    private static final String USERNAME = "^[A-Za-z][A-Za-z0-9_]{2,16}$";
    private static final String PASSWORD = "^(?=\\d*)(?=[a-z]*)(?=[A-Z]*)(?=[\\W]*).{2,16}$";

    public static boolean isValidUsername(String username) {
        return username.matches(USERNAME);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD);
    }
}
