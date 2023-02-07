package storage.JDBC;

import entity.Operation;

import java.util.Optional;

public interface OperationStorage {
    Operation findAll();
    Optional<Operation> findById(int id);
    void removeAll();
    void removeById(int id);
}
