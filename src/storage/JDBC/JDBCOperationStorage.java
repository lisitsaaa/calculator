package storage.JDBC;

import entity.Operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCOperationStorage implements OperationStorage {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

    private static final String INSERT = "insert into users values (default, ?, ?, ?)";

    @Override
    public Operation findAll() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
