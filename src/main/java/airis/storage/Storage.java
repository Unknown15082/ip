package airis.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import airis.AirisException;
import airis.task.Task;
import airis.task.TaskList;

public class Storage {
    private final Path path;

    public Storage(Path path) {
        this.path = path;
    }

    public static Storage localFile(String filename) {
        String cwd = System.getProperty("user.dir");
        Path path = Paths.get(cwd, filename);
        return new Storage(path);
    }

    public void createIfNotExists() throws AirisException {
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            throw new AirisException(e);
        }
    }

    public void export(TaskList tasklist) throws AirisException {
        ArrayList<String> contents = new ArrayList<>();
        for (Task item: tasklist.getAllTasks()) {
            contents.add(item.toSaveData());
        }

        try {
            Files.write(this.path, contents);
        } catch (IOException e) {
            throw new AirisException(e);
        }
    }

    public void load(TaskList tasklist) throws AirisException {
        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(this.path);
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

            tasklist.add(Task.loadTask(line));
        }
    }
}
