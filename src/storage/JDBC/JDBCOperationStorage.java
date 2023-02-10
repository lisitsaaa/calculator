package storage.JDBC;

import entity.Operation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOperationStorage implements OperationStorage {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

    private static final String INSERT = "insert into operations values (default,?,?,?,?)";
    private static final String SELECT_ALL_BY_USER_ID = "select * from operations where user_id = ?";
    private static final String SELECT_BY_ID = "select * from operations where id = ?";
    private static final String DELETE_BY_ID = "delete from operations where id = ?";
    private static final String DELETE_ALL = "delete from operations";


    @Override
    public void save(Operation operation) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, getNumbersString(operation.getNumbers()));
            preparedStatement.setDouble(2, operation.getResult());
            preparedStatement.setString(3, operation.getType().name());
            preparedStatement.setInt(4, operation.getOwner().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNumbersString(List<Double> numbers) {
        StringBuilder allStr = new StringBuilder();
        for (double number : numbers) {
            String numberStr = String.valueOf(number);
            allStr.append(numberStr).append(", ");
        }
        return allStr.toString();
    }

    @Override
    public List<Operation> findAll(int userId) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_USER_ID);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Operation> operations = new ArrayList<>();

            while (resultSet.next()) {
                Operation operation = getOperation(resultSet);
                operations.add(operation);
            }
            return operations;
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }

    private Operation getOperation(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String numbersStr = resultSet.getString(2);
        List<Double> numbers = getNumberList(numbersStr);
        double result = resultSet.getDouble(3);
        Operation.Type type = Operation.Type.valueOf(resultSet.getString(4));

        return new Operation(id, numbers, type, result);
    }

    private List<Double> getNumberList(String numbersStr) {
        String[] split = numbersStr.split(", ");
        List<Double> numbers = new ArrayList<>();
        for (String str : split) {
            numbers.add(Double.parseDouble(str));
        }
        return numbers;
    }

    @Override
    public Optional<Operation> findById(int id) {
        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Operation operation = getOperation(resultSet);

            return Optional.of(operation);
        } catch (SQLException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public void removeAll() {
        try(Connection connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute(DELETE_ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeById(int id) {
        try(Connection connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
