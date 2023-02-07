package service;

import entity.User;
import storage.JDBC.JDBCUserStorage;

import java.util.Optional;

public class UserService {
    private final JDBCUserStorage storage = new JDBCUserStorage();

    public void create(User user) {
        Thread thread = new Thread(() -> {
            storage.save(user);
        });
        thread.start();
    }

    public Optional<User> findByUsername(String username) {
        return storage.findByUserName(username);
    }
}
