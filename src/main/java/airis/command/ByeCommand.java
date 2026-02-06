package airis.command;

import java.util.HashMap;

import airis.storage.Storage;
import airis.task.TaskList;

public class ByeCommand implements Command {
    private ByeCommand() {
    }

    public static Command make(HashMap<String, String> args) {
        return new ByeCommand();
    }

    @Override
    public Response process(Storage storage, TaskList tasklist) {
        return Response.quitMessage("See you next time!");
    }
}
