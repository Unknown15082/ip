package airis;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Store a list of tasks, and perform operations on them.
 */
public class Storage {
    private final ArrayList<Task> memory;

    public Storage() {
        memory = new ArrayList<>(120);
    }

    /**
     * Add a task to the storage.
     * @param item The item to add.
     */
    public void add(Task item) {
        memory.add(item);
    }

    /**
     * Retrieve a task from the storage.
     * @param idx The index to retrieve.
     * @return The item.
     */
    public Task get(int idx) {
        return memory.get(idx);
    }

    /**
     * Remove a task from the storage.
     * @param idx The index of the item to be removed.
     * @return The deleted task.
     */
    public Task remove(int idx) {
        return memory.remove(idx);
    }

    /**
     * Return a representation of all tasks inside the storage.
     * @return A multiline string, containing the number of elements and the elements themselves.
     */
    public String getAllAsString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("There are %d tasks stored:\n", memory.size()));
        for (int i = 0; i < memory.size(); i++) {
            Task item = memory.get(i);
            str.append(String.format("%d: %s", i+1, item.toString()));
            if (i < memory.size() - 1) str.append("\n");
        }
        return str.toString();
    }

    /**
     * Save the storage to a local file.
     * @throws AirisException if there are I/O errors.
     */
    public void export() throws AirisException {
        String cwd = System.getProperty("user.dir");
        Path path = Paths.get(cwd, "data.txt");

        // Create if not exists
        try {
            Files.createDirectories(path.getParent());
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            throw new AirisException(e);
        }

        // Create file content
        ArrayList<String> contents = new ArrayList<>();
        for (Task item : memory) {
            contents.add(item.toSaveData());
        }

        // Write to file
        try {
            Files.write(path, contents);
        } catch (IOException e) {
            throw new AirisException(e);
        }
    }

    /**
     * Load the storage from a local file.
     * @throws AirisException if there is an I/O error.
     */
    public void load() throws AirisException {
        String cwd = System.getProperty("user.dir");
        Path path = Paths.get(cwd, "data.txt");

        BufferedReader reader;

        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new AirisException(e);
        }

        while (true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new AirisException(e);
            }
            if (line == null) break;

            this.add(Task.loadTask(line));
        }
    }
}
