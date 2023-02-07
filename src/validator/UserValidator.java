package validator;

import console.util.ConsoleWriter;

public class UserValidator {
    private static final String USERNAME = "^[A-Za-z][A-Za-z0-9_]{2,16}$";
    private static final String PASSWORD = "^(?=\\d*)(?=[a-z]*)(?=[A-Z]*)(?=[\\W]*).{2,16}$";

    public static boolean isValidUsername(String username){
        if(!username.matches(USERNAME)){
            ConsoleWriter.writeError(username + " is invalid username");
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String password){
        if(!password.matches(PASSWORD)){
            ConsoleWriter.writeError(password + " is invalid password");
            return false;
        }
        return true;
    }

}
