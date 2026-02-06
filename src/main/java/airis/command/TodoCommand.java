package airis.command;

import java.util.HashMap;

import airis.AirisException;
import airis.storage.Storage;
import airis.task.TaskList;
import airis.task.Todo;

public class TodoCommand implements Command {
    private final String description;

    private TodoCommand(String description) {
        this.description = description;
    }

    public static Command make(HashMap<String, String> args) throws AirisException {
        String description = args.get("main");
        if (description == null || description.isEmpty()) {
            throw new AirisException("Description cannot be empty");
        }
        return new TodoCommand(description);
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        Todo task = new Todo(this.description);
        tasklist.add(task);
        try {
            storage.export(tasklist);
        } catch (AirisException e) {
            return Response.fromException(e);
        }
        return Response.fromMessage(
                "I have added this task to your list:\n\t" + task
        );
    }
}
