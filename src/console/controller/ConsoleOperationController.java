package console.controller;

import console.ConsoleSession;
import entity.Operation;
import service.CalculatorService;

import java.util.List;
import java.util.Optional;

import static console.util.ConsoleMessage.*;
import static console.util.ConsoleReader.*;
import static console.util.ConsoleWriter.write;
import static console.util.ConsoleWriter.writeError;

public final class ConsoleOperationController {
    private static final CalculatorService calculator = new CalculatorService();

    public static void chooseHistoryOperation(ConsoleSession consoleSession) {
        write(INFORMATION_MENU);

        switch (readInteger()) {
            case 1 -> {
                List<Operation> allByUser = calculator.findAllByUser(consoleSession.getCurrentUser());
                allByUser.forEach(System.out::println);
            }
            case 2 -> {
                write(ID_MESSAGE);
                Optional<Operation> infoByInd = calculator.findByIdByUser(readInteger());
                infoByInd.ifPresent(operation -> write(operation.toString()));
            }
//            case 3 -> calculator.removeAllByUser(consoleSession.getCurrentUser());
            case 4 -> {
                write(ID_MESSAGE);
                calculator.removeById(readInteger());
            }
        }
    }
    public static void createOperation(ConsoleSession consoleSession) {
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

    private static Optional<Operation.Type> readOperationType() {
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
}
