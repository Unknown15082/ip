import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Storage<T extends Task> {
    private final ArrayList<T> memory;

    public Storage() {
        memory = new ArrayList<>(120);
    }

    public void add(T item) {
        memory.add(item);
    }

    public T get(int idx) {
        return memory.get(idx);
    }

    public T remove(int idx) {
        return memory.remove(idx);
    }

    public String getAllAsString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("There are %d tasks stored:\n", memory.size()));
        for (int i = 0; i < memory.size(); i++) {
            T item = memory.get(i);
            str.append(String.format("%d: %s", i+1, item.toString()));
            if (i < memory.size() - 1) str.append("\n");
        }
        return str.toString();
    }

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
        for (T item : memory) {
            contents.add(item.toSaveData());
        }

        // Write to file
        try {
            Files.write(path, contents);
        } catch (IOException e) {
            throw new AirisException(e);
        }
    }
}
