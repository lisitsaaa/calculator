package storage.inMemory;

import entity.Operation;

import java.util.List;

public interface OperationStorage {
    void save(Operation result);
    List<Operation> findAll(int userId);
    List<Operation> findById(int operationId);
    void removeAll(int userId);
    void removeById(int operationId);




}
