package console;

import entity.Operation;
import entity.User;
import service.CalculatorService;
import service.UserService;
import validator.UserValidator;

import java.util.List;
import java.util.Optional;

import static console.util.ConsoleMessage.*;
import static console.util.ConsoleReader.*;
import static console.util.ConsoleWriter.*;


public class ConsoleApplication {
    private ConsoleSession consoleSession;
    private final UserService userService = new UserService();
    private final CalculatorService calculator = new CalculatorService();

    public void run() {
        while (true) {
            if (consoleSession == null) {
                write(EXTERNAL_MENU);

                switch (readInteger()) {
                    case 1 -> signUp();
                    case 2 -> logIn();
                    case 3 -> {
                        write(BYE_MESSAGE);
                        return;
                    }
                }
            } else {
                write(consoleSession.getCurrentUser().getName() + CHOICE_MESSAGE);
                write(INTERNAL_MENU);

                switch (readInteger()) {
                    case 1 -> calculate(consoleSession);
                    case 2 -> chooseHistoryOperation();
                    case 3 -> consoleSession = null;
                    case 4 -> {
                        write(BYE_MESSAGE);
                        return;
                    }
                }
            }
        }
    }

    private String readInfo(String message) {
        write(message);
        return readString();
    }

    private String validateUsername() {
        boolean validator;
        String username;
        do {
            username = readInfo(USERNAME_MESSAGE);
            validator = UserValidator.isValidUsername(username);
            if(!validator) writeError(username + INVALID_USERNAME);
        } while (!validator);
        return username;
    }

    private String validatePassword() {
        boolean validator;
        String password;
        do {
            password = readInfo(PASSWORD_MESSAGE);
            validator = UserValidator.isValidPassword(password);
            if(!validator) writeError(password + INVALID_USERNAME);
        } while (!validator);
        return password;
    }

    private void signUp() {
        String username = validateUsername();
        String password = validatePassword();
        String name = readInfo(NAME_MESSAGE);

        User regUser = new User(username, password, name);
        userService.create(regUser);
    }

    private void logIn() {
        String username = validateUsername();
        String password = validatePassword();

        Optional<User> byUsername = userService.findByUsername(username);
        if (byUsername.isPresent()) {
            User authorizationUser = byUsername.get();
            if (authorizationUser.getPassword().equals(password)) {
                consoleSession = new ConsoleSession(authorizationUser);
            } else {
                writeError(WRONG_PASSWORD_MESSAGE);
            }
        } else {
            writeError(USER_NOT_FOUND_MESSAGE);
        }
    }

    public void calculate(ConsoleSession consoleSession) {
        write(OPERATION_TYPE_MENU);
        Optional<Operation.Type> type = readOperationType();

        if (type.isPresent()) {
            Operation.Type operationType = type.get();

            write(NUMBERS_MESSAGE);
            List<Double> numbers = readDoubleList();

            Operation operate = new Operation(numbers, operationType, consoleSession.getCurrentUser());
            Optional<Operation> result = calculator.calculate(operate);

            write(result.toString());
        } else {
            writeError(INCORRECT_TYPE);
        }
    }

    private Optional<Operation.Type> readOperationType() {
        String strType = readString().toUpperCase();
        switch (strType) {
            case "SUM" -> {
                return Optional.of(Operation.Type.SUM);
            }
            case "SUB" -> {
                return Optional.of(Operation.Type.SUB);
            }
            case "MUL" -> {
                return Optional.of(Operation.Type.MUL);
            }
            case "DIV" -> {
                return Optional.of(Operation.Type.DIV);
            }
        }
        return Optional.empty();
    }

    private void chooseHistoryOperation() {
        write(INFORMATION_MENU);

        switch (readInteger()) {
            case 1 -> {
                List<Operation> allByUser = calculator.findAllByUser(consoleSession.getCurrentUser());
                allByUser.forEach(System.out::println);
            }
            case 2 -> {
                write(ID_MESSAGE);
                List<Operation> infoByInd = calculator.findByIdByUser(readInteger());
                infoByInd.forEach(System.out::println);
            }
            case 3 -> calculator.removeAllByUser(consoleSession.getCurrentUser());
            case 4 -> {
                write(ID_MESSAGE);
                calculator.removeByIdByUser(readInteger());
            }
        }
    }
}