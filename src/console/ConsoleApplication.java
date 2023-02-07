package console;

import entity.Operation;
import entity.User;
import service.CalculatorService;
import service.UserService;
import validator.UserValidator;

import java.util.List;
import java.util.Optional;

import static console.util.ConsoleReader.*;
import static console.util.ConsoleWriter.write;
import static console.util.ConsoleWriter.writeError;


public class ConsoleApplication {
    private ConsoleSession consoleSession;
    private final UserService userService = new UserService();
    private final CalculatorService calculator = new CalculatorService();
    
    public void run() {
        while (true) {
            if (consoleSession == null) {
                write("""
                        hello!
                        1 - registration
                        2 - authorization
                        3 - exit""");

                switch (readInteger()) {
                    case 1 -> signUp();
                    case 2 -> logIn();
                    case 3 -> {
                        write("see you!");
                        return;
                    }
                }
            } else {
                write(consoleSession.getCurrentUser().getName() + ", you should make your choice");
                write("""
                        1 - calculator
                        2 - history
                        3 - logout
                        4 - exit""");

                switch (readInteger()) {
                    case 1 -> calculate(consoleSession);
                    case 2 -> chooseHistoryOperation();
                    case 3 -> consoleSession = null;
                    case 4 -> {
                        write("see you!");
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

    private void signUp() {
//        String username = readInfo("enter username");
//        String password = readInfo("enter password");

        String username = validateInfo("enter username", 1);
        String password = validateInfo("enter password", 2);
        String name = readInfo("enter name");

        User regUser = new User(username, password, name);
        userService.create(regUser);
    }

    private String validateInfo(String message, int code) {
        boolean validator = false;
        String info;
        do {
            info = readInfo(message);
            switch (code) {
                case 1 -> validator = UserValidator.isValidUsername(info);
                case 2 -> validator = UserValidator.isValidPassword(info);
            }
        } while (!validator);
        return info;
    }

    private void logIn() {
//        String username = readInfo("enter username");
//        String password = readInfo("enter password");
        String username = validateInfo("enter username", 1);
        String password = validateInfo("enter password", 2);

        Optional<User> byUsername = userService.findByUsername(username);
        if (byUsername.isPresent()) {
            User authorizationUser = byUsername.get();
            if (authorizationUser.getPassword().equals(password)) {
                consoleSession = new ConsoleSession(authorizationUser);
            } else {
                writeError("Wrong password!");
            }
        } else {
            writeError("User not found!");
        }
    }

    public void calculate(ConsoleSession consoleSession) {
        write("""
                sum <- "+"
                sub <- "-"
                mul <- "*"
                div <- "/" """);
        Optional<Operation.Type> type = readOperationType();

        if (type.isPresent()) {
            Operation.Type operationType = type.get();

            write("enter numbers:");
            List<Double> numbers = readDoubleList();

            Operation operate = new Operation(numbers, operationType, consoleSession.getCurrentUser());
            Optional<Operation> result = calculator.calculate(operate);

            write(result.toString());
        } else {
            writeError("incorrect type");
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
        write("""
                1 - get all info
                2 - get info by id
                3 - remove all info
                4 - remove info by id""");

        switch (readInteger()) {
            case 1 -> {
                List<Operation> allByUser = calculator.findAllByUser(consoleSession.getCurrentUser());
                allByUser.forEach(System.out::println);
            }
            case 2 -> {
                write("enter id:");
                List<Operation> infoByInd = calculator.findByIdByUser(readInteger());
                infoByInd.forEach(System.out::println);
            }
            case 3 -> calculator.removeAllByUser(consoleSession.getCurrentUser());
            case 4 -> {
                write("enter id:");
                calculator.removeByIdByUser(readInteger());
            }
        }
    }
}