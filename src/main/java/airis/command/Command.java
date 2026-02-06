package airis.command;

import airis.task.TaskList;

/**
 * The interface for all types of commands.
 */
public interface Command {
    Response process(TaskList tasklist);
}
