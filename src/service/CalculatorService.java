package service;

import entity.Operation;
import entity.User;
import storage.JDBC.JDBCOperationStorage;
import storage.JDBC.OperationStorage;

import java.util.List;
import java.util.Optional;

public class CalculatorService {
    private final OperationStorage storage = new JDBCOperationStorage();

    public Optional<Operation> calculate(Operation operation) {
        switch ( operation.getType()) {
            case SUM -> {
                operation.setResult(addition(operation.getNumbers()));
                storage.save(operation);
                return Optional.of(operation);
            }
            case SUB -> {
                operation.setResult(subtraction(operation.getNumbers()));
                storage.save(operation);
                return Optional.of(operation);
            }
            case MUL -> {
                operation.setResult(multiplication(operation.getNumbers()));
                storage.save(operation);
                return Optional.of(operation);
            }
            case DIV -> {
                operation.setResult(division(operation.getNumbers()));
                storage.save(operation);
                return Optional.of(operation);
            }
        }
        return Optional.empty();
    }

    private double addition(List<Double> numbers) {
        double res = 0;
        for (Double d : numbers) {
            res += d;
        }
        return res;
    }

    private double subtraction(List<Double> numbers) {
        double res = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if (i == 0) {
                res = numbers.get(i);
            } else {
                res -= numbers.get(i);
            }
        }
        return res;
    }

    private double multiplication(List<Double> numbers) {
        double res = 1;
        for (Double d : numbers) {
            res *= d;
        }
        return res;
    }

    private double division(List<Double> numbers) {
        double res = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if (i == 0) {
                res = numbers.get(i);
            } else {
                res /= numbers.get(i);
            }
        }
        return res;
    }

    public List<Operation> findAllByUser(User user) {
        return storage.findAll(user.getId());
    }

    public Optional<Operation> findByIdByUser(int id) {
        return storage.findById(id);
    }
//
//    public void removeAllByUser(User user) {
//        storage.removeAll(user.getId());
//    }

    public void removeById(int id) {
        storage.removeById(id);
    }
}
