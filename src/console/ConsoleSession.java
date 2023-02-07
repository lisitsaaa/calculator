package console;

import entity.User;

public class ConsoleSession {
    private User currentUser;

    public ConsoleSession(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
