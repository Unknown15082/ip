package airis.command;

import java.util.HashMap;

import airis.AirisException;
import airis.task.Task;
import airis.task.TaskList;

public class UnmarkCommand implements Command {
    private int index;

    private UnmarkCommand(int index) {
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
        return new UnmarkCommand(index - 1);
    }

    @Override
    public Response process(TaskList tasklist) {
        Task task;
        try {
            task = tasklist.get(this.index);
        } catch (IndexOutOfBoundsException e) {
            return Response.fromErrorMessage("Index is out of bounds");
        }

        task.markAsNotDone();

        return Response.fromMessage(String.format("I've mark this as not done yet:\n\t%s", task));
    }
}
