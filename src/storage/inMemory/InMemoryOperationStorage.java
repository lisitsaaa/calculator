package storage.inMemory;

import entity.Operation;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOperationStorage implements OperationStorage {
    private final List<Operation> store = new ArrayList<>();
    private int ids = 1;

    @Override
    public void save(Operation operate) {
        operate.setId(ids++);
        store.add(operate);
    }

    @Override
    public List<Operation> findAll(int userId) {
        return store.stream()
                .filter(operation -> operation.getOwner().getId() == userId)
                .toList();
    }

    @Override
    public List<Operation> findById(int operationId) {
        return store.stream()
                .filter(operation -> operation.getId() == operationId)
                .toList();
    }

    @Override
    public void removeAll(int userId) {
        store.removeAll(findAll(userId));
    }

    @Override
    public void removeById(int operationId) {
        store.removeIf(operation -> operation.getId() == operationId);
    }
}
