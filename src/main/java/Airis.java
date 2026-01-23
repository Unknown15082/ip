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
            String instruction = input.next();
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
                    index = input.nextInt();
                    task = storage.get(index - 1);
                    task.markAsDone();
                    printMessage(String.format(doneMessage, task));
                    break;
                case "unmark":
                    index = input.nextInt();
                    task = storage.get(index - 1);
                    task.markAsNotDone();
                    printMessage(String.format(notDoneMessage, task));
                    break;
                case "todo":
                    String description = input.nextLine();
                    description = description.trim();
                    storage.add(new Task(description));
                    printMessage("added: " + description);
                    break;
                default:
                    printMessage("Sorry, I don't know what this command means =(");
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
