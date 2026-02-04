package airis.command;

import java.util.HashMap;

import airis.task.TaskList;

public class ByeCommand implements Command {
    private ByeCommand() {
    }

    public static Command make(HashMap<String, String> args) {
        return new ByeCommand();
    }

    @Override
    public Response process(TaskList tasklist) {
        return Response.quitMessage("See you next time!");
    }
}
