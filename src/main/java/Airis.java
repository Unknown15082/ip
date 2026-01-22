import java.util.Scanner;

public class Airis {
    private static final String helloMessage = """
        Hello! I'm Airis! Nice to meet you!
        What can I do for you?""";

    private static final String byeMessage = """
        See you next time!""";

    private static final Storage<Task> storage = new Storage<>();

    public static void main(String[] args) {
        printMessage(helloMessage);

        Scanner input = new Scanner(System.in);
        while (true) {
            String command = input.nextLine();
            if (command.equals("bye")) {
                printMessage(byeMessage);
                break;
            } else if (command.equals("list")) {
                printMessage(storage.getAllAsString());
            } else {
                storage.add(new Task(command));
                printMessage("added: " + command);
            }
        }
    }

    static String wrapMessage(String msg) {
        String hline = "_".repeat(50);
        return hline + "\n" + msg + "\n" + hline;
    }

    static void printMessage(String msg) {
        System.out.println(wrapMessage(msg));
    }
}
