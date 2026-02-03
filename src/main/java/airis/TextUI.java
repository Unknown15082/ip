package airis;

public class TextUI implements UI {
    public TextUI() {
    }

    private String wrapMessage(String message) {
        String hline = "_".repeat(50);
        return hline + "\n" + message + "\n" + hline;
    }

    @Override
    public void display(String message) {
        System.out.println(this.wrapMessage(message));
    }
}
