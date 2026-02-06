package airis.command;

import airis.AirisException;
import airis.ui.UI;

public class Response {
    private final String message;
    private boolean quitAfterRun;

    private Response(String message) {
        this.message = message;
        this.quitAfterRun = false;
    }

    public static Response fromMessage(String message) {
        return new Response(message);
    }

    public static Response quitMessage(String message) {
        Response response = new Response(message);
        response.quitAfterRun = true;
        return response;
    }

    public static Response fromException(AirisException e) {
        return new Response(e.getAirisMessage());
    }

    public static Response fromErrorMessage(String message) {
        AirisException e = new AirisException(message);
        return Response.fromException(e);
    }

    public void process(UI ui) {
        ui.display(this.message);
        if (this.quitAfterRun) {
            System.exit(0);
        }
    }
}
