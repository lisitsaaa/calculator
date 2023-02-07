package console.controller;

import console.ConsoleSession;
import entity.User;
import service.UserService;
import validator.UserValidator;

import java.util.Comparator;
import java.util.Optional;

import static console.util.ConsoleMessage.*;
import static console.util.ConsoleMessage.INVALID_USERNAME;
import static console.util.ConsoleReader.readString;
import static console.util.ConsoleWriter.write;
import static console.util.ConsoleWriter.writeError;

public final class ConsoleUserController {
    private static final UserService userService = new UserService();

    public static void signUp() {
        String username = validateUsername();
        String password = validatePassword();
        write(NAME_MESSAGE);
        String name = readString();

        User regUser = new User(username, password, name);
        userService.create(regUser);
    }

    public static Optional<User> logIn() {
        String username = validateUsername();
        String password = validatePassword();

        Optional<User> byUsername = userService.findByUsername(username);

        if (byUsername.isPresent()) {
            User authorizationUser = byUsername.get();
            if (authorizationUser.getPassword().equals(password)) {
                return Optional.of(authorizationUser);
            } else {
                writeError(WRONG_PASSWORD_MESSAGE);
            }
        } else {
            writeError(USER_NOT_FOUND_MESSAGE);
        }
        return Optional.empty();
    }

    private static String readInfo(String message) {
        write(message);
        return readString();
    }

    private static String validateUsername() {
        boolean validator;
        String username;
        do {
            username = readInfo(USERNAME_MESSAGE);
            validator = UserValidator.isValidUsername(username);
            if(!validator) writeError(username + INVALID_USERNAME);
        } while (!validator);
        return username;
    }

    private static String validatePassword() {
        boolean validator;
        String password;
        do {
            password = readInfo(PASSWORD_MESSAGE);
            validator = UserValidator.isValidPassword(password);
            if(!validator) writeError(password + INVALID_PASSWORD);
        } while (!validator);
        return password;
    }
}
