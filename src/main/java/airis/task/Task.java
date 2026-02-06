package airis.task;

import airis.AirisException;

/**
 * The base class for all types of tasks.
 */
public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Load a task from its text representation.
     *
     * @param data The text representation of a task.
     * @return The parsed task.
     * @throws AirisException if the task type is invalid.
     */
    public static Task loadTask(String data) throws AirisException {
        return switch (data.charAt(0)) {
            case 'T' -> Todo.loadTask(data);
            case 'D' -> Deadline.loadTask(data);
            case 'E' -> Event.loadTask(data);
            default -> throw new AirisException("Invalid task type");
        };
    }

    /**
     * Returns the description.
     *
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    private String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Return the text representation to be saved.
     *
     * @return The text representation.
     */
    public String toSaveData() {
        return String.format("%b|%s", this.isDone, this.description);
    }

    /**
     * Mark the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Mark the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }
}
