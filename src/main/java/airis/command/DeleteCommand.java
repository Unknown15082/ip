package airis.command;

import java.util.HashMap;

import airis.AirisException;
import airis.storage.Storage;
import airis.task.Task;
import airis.task.TaskList;

public class DeleteCommand implements Command {
    private final int index;

    private DeleteCommand(int index) {
        this.index = index;
    }

    public static Command make(HashMap<String, String> args) throws AirisException {
        String indexStr = args.get("main");
        if (indexStr == null || indexStr.isEmpty()) {
            throw new AirisException("Index cannot be empty");
        }
        int index;
        try {
            index = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new AirisException("Cannot convert index to integer");
        }
        return new DeleteCommand(index - 1);
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        Task task;
        try {
            task = tasklist.remove(this.index);
        } catch (IndexOutOfBoundsException e) {
            return Response.fromErrorMessage("Index is out of bounds");
        }

        return Response.fromMessage(String.format("I've deleted this task:\n\t%s", task));
    }
}
