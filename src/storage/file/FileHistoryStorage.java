package storage.file;

import entity.Operation;

import java.io.*;

import static console.util.ConsoleWriter.write;

public class FileHistoryStorage implements HistoryStorage {
    private final String fileName = "history.txt";
    private int ids = 1;

    private void checkFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void writeHistory(Operation result) {
        checkFile();
        result.setId(ids++);

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(result.toString() + System.lineSeparator());
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findAll() {
        checkFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String history;

            while ((history = reader.readLine()) != null) {
                write(history);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        write("--------------------------------");
    }

    @Override
    public void removeAll() {
        checkFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false))) {
            bufferedWriter.write("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void findById(int id) {
        checkFile();

        String subString = "id = " + id;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String history;

            while ((history = reader.readLine()) != null) {
                if(history.contains(subString)){
                    write(history);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeById(int id) {
        checkFile();
        String subString = "id = " + id;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            String history;

            while ((history = reader.readLine()) != null) {
                if(history.contains(subString)){
                    history = " ";
                    bufferedWriter.write(history);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
