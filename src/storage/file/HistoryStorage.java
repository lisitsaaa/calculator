package storage.file;

import entity.Operation;

public interface HistoryStorage {
    void writeHistory(Operation result);
    void removeAll();
    void removeById(int id);
    void findById(int id);
    void findAll();
}
