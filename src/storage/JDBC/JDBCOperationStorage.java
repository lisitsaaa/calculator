package storage.JDBC;

import entity.Operation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JDBCOperationStorage implements OperationStorage {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

    private static final String INSERT = "insert into operations values (default,?,?,?)";
    private static final String SELECT_ALL = "select * from operations";


    @Override
    public void save(Operation operation) {
       try(Connection connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD)) {
           PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
           preparedStatement.setString(1,getNumbersString(operation.getNumbers()));
           preparedStatement.setDouble(2, operation.getResult());
           preparedStatement.setString(3, String.valueOf(operation.getType()));
           preparedStatement.execute();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    private String getNumbersString(List<Double> numbers){
        StringBuilder allStr = new StringBuilder();
        for(double number : numbers){
            String numberStr = String.valueOf(number);
            allStr.append(numberStr).append(", ");
        }
        return allStr.toString();
    }


    @Override
    public List<Operation> findAll() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            List<Operation> operations = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String numbersStr = resultSet.getString(2);
                List<Double> numbers = getList(numbersStr);
                double result = resultSet.getDouble(3);
                Operation.Type type = Operation.Type.valueOf(resultSet.getString(4));

                Operation operation = new Operation(id,numbers,type,result);
                operations.add(operation);
            }
            return operations;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

    public List<Double> getList(String numbersStr){
        String[] split = numbersStr.split(", ");
        List<Double> numbers = new ArrayList<>();
        for (String str : split){
            numbers.add(Double.parseDouble(str));
        }
        return numbers;
    }

    @Override
    public Optional<Operation> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void removeAll() {

    }

    @Override
    public void removeById(int id) {

    }
}
