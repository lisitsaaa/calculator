package service;

import entity.User;
import storage.JDBC.JDBCUserStorage;

import java.util.Optional;

public class UserService {
    private final JDBCUserStorage storage = new JDBCUserStorage();
    
    public void create(User user) {
        storage.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return storage.findByUserName(username);
    }
}
