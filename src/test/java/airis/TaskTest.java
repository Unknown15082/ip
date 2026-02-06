package airis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import airis.task.Task;

public class TaskTest {
    @Test
    public void loadTaskTest_Todo() throws AirisException {
        Task task = Task.loadTask("T|false|sleep");
        assertEquals("[T][ ] sleep", task.toString());
    }

    @Test
    public void loadTaskTest_Deadline() throws AirisException {
        Task task = Task.loadTask("D|false|iP|2026-02-06T16:00:00");
        assertEquals("[D][ ] iP (by: 2026-02-06 16:00:00)", task.toString());
    }

    @Test
    public void loadTaskTest_Event() throws AirisException {
        Task task = Task.loadTask(
                "E|false|meeting|2026-02-04T18:00:00|2026-02-04T20:00:00");
        assertEquals(
                "[E][ ] meeting (from: 2026-02-04 18:00:00; to: 2026-02-04 20:00:00)",
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
