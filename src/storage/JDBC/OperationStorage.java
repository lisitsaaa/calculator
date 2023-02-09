package storage.JDBC;

import entity.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationStorage {

    void save(Operation operation);
    List<Operation> findAll();
    Optional<Operation> findById(int id);
    void removeAll();
    void removeById(int id);
}
