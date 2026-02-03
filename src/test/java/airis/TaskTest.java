package airis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    public void loadTaskTest_Todo() throws AirisException {
        Task task = Task.loadTask("T|false|sleep");
        assertEquals("[T][ ] sleep", task.toString());
    }

    @Test
    public void loadTaskTest_Deadline() throws AirisException {
        Task task = Task.loadTask("D|false|iP|Feb 06 2026 16:00");
        assertEquals("[D][ ] iP (by: 2026-02-06 16:00:00)", task.toString());
    }

    @Test
    public void loadTaskTest_Event() throws AirisException {
        Task task = Task.loadTask(
                "E|false|meeting|Feb 04 2026 18:00|Feb 04 2026 20:00");
        assertEquals(
                "[E][ ] meeting (from: 2026-02-04 18:00:00; by: 2026-02-04 20:00:00)",
                task.toString()
        );
    }

    @Test
    public void loadTaskTest_done() throws AirisException {
        Task task = Task.loadTask("T|true|homework");
        assertEquals("[T][X] homework", task.toString());
    }

    @Test
    public void markAsDoneTest() {
        Task task = new Task("test", false);
        task.markAsDone();
        assertEquals("[X] test", task.toString());
    }

    @Test
    public void markAsNotDoneTest() {
        Task task = new Task("documentation", true);
        task.markAsNotDone();
        assertEquals("[ ] documentation", task.toString());
    }
}
