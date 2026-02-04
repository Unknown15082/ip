package airis.command;

import java.util.HashMap;

public class ByeCommand implements Command {
    private ByeCommand() {
    }

    public static Command make(HashMap<String, String> args) {
        return new ByeCommand();
    }

    @Override
    public Response process() {
        return Response.quitMessage("See you next time!");
    }
}
