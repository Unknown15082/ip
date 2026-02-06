package airis.command;

import airis.storage.Storage;
import airis.task.TaskList;

/**
 * The interface for all types of commands.
 */
public interface Command {
    Response process(Storage storage, TaskList tasklist);
}
