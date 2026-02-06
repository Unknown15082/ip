package airis.command;

import java.util.HashMap;

import airis.AirisException;
import airis.storage.Storage;
import airis.task.Task;
import airis.task.TaskList;

public class MarkCommand implements Command {
    private final int index;

    private MarkCommand(int index) {
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
        return new MarkCommand(index - 1);
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        Task task;
        try {
            task = tasklist.get(this.index);
        } catch (IndexOutOfBoundsException e) {
            return Response.fromErrorMessage("Index is out of bounds");
        }

        task.markAsDone();
        try {
            storage.export(tasklist);
        } catch (AirisException e) {
            return Response.fromException(e);
        }

        return Response.fromMessage(String.format("I've mark this as done:\n\t%s", task));
    }
}
