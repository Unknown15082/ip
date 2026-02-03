package airis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    @Test
    public void addTest() {
        Storage storage = new Storage();
        Task task = new Task("test");
        storage.add(task);
        assertEquals(task, storage.get(0));
    }

    @Test
    public void addTest_multiple() {
        Storage storage = new Storage();
        Task task1 = new Task("test");
        Task task2 = new Task("try");
        Task task3 = new Task("attempt");
        storage.add(task1);
        storage.add(task2);
        storage.add(task3);
        assertEquals(task3, storage.get(2));
    }

    @Test
    public void removeTest() {
        Storage storage = new Storage();
        Task task1 = new Task("test");
        Task task2 = new Task("try");
        Task task3 = new Task("attempt");
        storage.add(task1);
        storage.add(task2);
        storage.add(task3);
        storage.remove(2);
        assertEquals(task2, storage.get(1));
        storage.remove(0);
        assertEquals(task2, storage.get(0));
    }
}
