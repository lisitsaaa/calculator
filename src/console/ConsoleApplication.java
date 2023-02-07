package console;

import entity.User;

import java.util.Optional;

import static console.controller.ConsoleOperationController.*;
import static console.controller.ConsoleUserController.*;
import static console.util.ConsoleMessage.*;
import static console.util.ConsoleReader.*;
import static console.util.ConsoleWriter.*;


public class ConsoleApplication {
    private ConsoleSession consoleSession;

    public void run() {
        while (true) {
            if (consoleSession == null) {
                write(EXTERNAL_MENU);
                switch (readInteger()) {
                    case 1 -> signUp();
                    case 2 -> {
                        Optional<User> user = logIn();
                        if (user.isPresent()) {
                            User authorizationUser = user.get();
                            consoleSession = new ConsoleSession(authorizationUser);
                        }
                    }
                    case 3 -> {
                        write(BYE_MESSAGE);
                        return;
                    }
                }
            } else {
                write(consoleSession.getCurrentUser().getName() + CHOICE_MESSAGE);
                write(INTERNAL_MENU);
                switch (readInteger()) {
                    case 1 -> createOperation(consoleSession);
                    case 2 -> chooseHistoryOperation(consoleSession);
                    case 3 -> consoleSession = null;
                    case 4 -> {
                        write(BYE_MESSAGE);
                        return;
                    }
                }
            }
        }
    }
}