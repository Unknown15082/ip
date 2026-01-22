import java.util.Scanner;

public class Airis {
    private static final String helloMessage = """
        Hello! I'm Airis! Nice to meet you!
        What can I do for you?""";

    private static final String byeMessage = """
        See you next time!""";

    private static final String doneMessage = """
        I've mark this as done:
            %s""";

    private static final String notDoneMessage = """
        I've mark this as not done yet:
            %s""";

    private static final Storage<Task> storage = new Storage<>();

    public static void main(String[] args) {
        printMessage(helloMessage);

        Scanner input = new Scanner(System.in);
        while (true) {
            String command = input.nextLine();
            String[] tokens = command.split(" ");
            String instruction = tokens[0];
            boolean quit = false;
            int index; Task task;

            switch (instruction) {
                case "bye":
                    printMessage(byeMessage);
                    quit = true;
                    break;
                case "list":
                    printMessage(storage.getAllAsString());
                    break;
                case "mark":
                    // TODO: Place this in a try-catch block
                    // Since this will fail for a command such as "mark as done"
                    index = Integer.parseInt(tokens[1]);
                    task = storage.get(index - 1);
                    task.markAsDone();
                    printMessage(String.format(doneMessage, task));
                    break;
                case "unmark":
                    // TODO: Same as above
                    index = Integer.parseInt(tokens[1]);
                    task = storage.get(index - 1);
                    task.markAsNotDone();
                    printMessage(String.format(notDoneMessage, task));
                    break;
                default:
                    storage.add(new Task(command));
                    printMessage("added: " + command);
            }

            if (quit) break;
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
