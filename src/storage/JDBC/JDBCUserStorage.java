package storage.JDBC;

import entity.User;

import java.sql.*;
import java.util.Optional;

public class JDBCUserStorage {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";
    private static final String INSERT = "insert into users values (default, ?, ?, ?)";
    private static final String SELECT_BY_ID = "select * from users where user_name = ?";

    public void save(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByUserName(String userName) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setString(1, userName);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(4);

            User user = new User(id, userName, password, name);
            return Optional.of(user);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
