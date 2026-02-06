package airis.command;

import java.util.HashMap;

import airis.storage.Storage;
import airis.task.Task;
import airis.task.TaskList;

public class ListCommand implements Command {
    private ListCommand() {
    }

    public static Command make(HashMap<String, String> args) {
        return new ListCommand();
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        Task[] tasks = tasklist.getAllTasks();

        int count = tasks.length;
        if (count == 0) {
            return Response.fromMessage("No tasks stored");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("There are %d tasks stored:", count));
            for (int i = 0; i < count; i++) {
                sb.append(String.format("\n%d: %s", i + 1, tasks[i]));
            }
            return Response.fromMessage(sb.toString());
        }
    }
}
