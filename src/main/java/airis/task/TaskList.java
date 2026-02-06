package airis.task;

import java.util.ArrayList;

/**
 * A list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> list;

    /**
     * Default constructor
     */
    public TaskList() {
        list = new ArrayList<>();
    }

    /**
     * Add an item into the list
     *
     * @param item The item to be added.
     */
    public void add(Task item) {
        list.add(item);
    }

    /**
     * Remove an item from the list, using its index.
     *
     * @param index The index of the item to be removed.
     * @return The removed task.
     * @throws IndexOutOfBoundsException if index is invalid.
     */
    public Task remove(int index) {
        return list.remove(index);
    }

    /**
     * Retrieve an item from the list, using its index.
     *
     * @param index The index of the item to be retrieved.
     * @return The retrieved task.
     * @throws IndexOutOfBoundsException if index is invalid.
     */
    public Task get(int index) {
        return list.get(index);
    }

    /**
     * Return an array of all the stored tasks.
     *
     * @return The array of tasks.
     */
    public Task[] getAllTasks() {
        return list.toArray(new Task[0]);
    }
}
