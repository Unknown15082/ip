package airis.command;

import airis.AirisException;
import airis.ui.UI;

/**
 * Response encapsulates the different possible message and state the program can output.
 */
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

    /**
     * Create a message that quits the program after resolving.
     * @param message Message.
     * @return The created response.
     */
    public static Response quitMessage(String message) {
        Response response = new Response(message);
        response.quitAfterRun = true;
        return response;
    }

    public static Response fromException(AirisException e) {
        return new Response(e.getAirisMessage());
    }

    /**
     * Construct a response from an error message.
     * @param message The error message.
     * @return The created response.
     */
    public static Response fromErrorMessage(String message) {
        AirisException e = new AirisException(message);
        return Response.fromException(e);
    }

    /**
     * Use the UI to process the response.
     * @param ui The UI object.
     */
    public void process(UI ui) {
        ui.display(this.message);
        if (this.quitAfterRun) {
            System.exit(0);
        }
    }
}
