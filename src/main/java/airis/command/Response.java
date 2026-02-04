package airis.command;

public class Response {
    private String message;
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
}
