package airis.command;

import java.util.HashMap;

import airis.AirisException;
import airis.storage.Storage;
import airis.task.Task;
import airis.task.TaskList;

public class FindCommand implements Command {
    private final String keyword;

    private FindCommand(String keyword) {
        this.keyword = keyword;
    }

    public static Command make(HashMap<String, String> args) throws AirisException {
        String keyword = args.get("main");
        if (keyword == null || keyword.isEmpty()) {
            throw new AirisException("Keyword cannot be empty");
        }

        return new FindCommand(keyword);
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        Task[] tasks = tasklist.search(this.keyword);

        int count = tasks.length;
        if (count == 0) {
            return Response.fromMessage("No tasks matched");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("There are %d matches:", count));
            for (Task task : tasks) {
                sb.append(String.format("\n%s", task));
            }
            return Response.fromMessage(sb.toString());
        }
    }
}
