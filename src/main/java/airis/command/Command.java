package airis.command;

import airis.task.TaskList;

/**
 * The interface for all types of commands.
 */
public interface Command {
    public Response process(TaskList tasklist);
}
