package storage.inMemory;

import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class InMemoryUserStorage {
    private int ids = 1;
    private final List<User> users = new ArrayList<>();
    public void save(User user) {
        user.setId(ids++);
        users.add(user);
    }

    public Optional<User> findByUserName(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
