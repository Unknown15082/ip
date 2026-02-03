package airis;

/**
 * An implementation of UI, that prints to standard output.
 */
public class TextUI implements UI {
    public TextUI() {
    }

    /**
     * Wrap the message in horizontal lines.
     * @param message The content of the message.
     * @return The wrapped message.
     */
    private String wrapMessage(String message) {
        String hline = "_".repeat(50);
        return hline + "\n" + message + "\n" + hline;
    }

    /**
     * Print the message to the standard output.
     * @param message The message to be displayed.
     */
    @Override
    public void display(String message) {
        System.out.println(this.wrapMessage(message));
    }
}
