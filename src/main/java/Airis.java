import java.util.*;

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

    private static final String deleteMessage = """
        I've deleted this task:
            %s""";

    private static final Storage<Task> storage = new Storage<>();

    public static void main(String[] args) {
        printMessage(helloMessage);

        Scanner input = new Scanner(System.in);
        while (true) {
            String instruction = input.next();

            try {
                switch (instruction) {
                    case "bye":
                        quitProgram();
                        return;
                    case "list":
                        printMessage(storage.getAllAsString());
                        input.nextLine(); // Consume current line
                        break;
                    case "mark": {
                        handleMark(input);
                        break;
                    }
                    case "unmark": {
                        handleUnmark(input);
                        break;
                    }
                    case "delete": {
                        handleDelete(input);
                        break;
                    }
                    case "todo": {
                        handleTodo(input);
                        break;
                    }
                    case "deadline": {
                        handleDeadline(input);
                        break;
                    }
                    case "event": {
                        handleEvent(input);
                        break;
                    }
                    default:
                        throw new AirisException("Sorry, I don't know what this command means :(");
                }
            } catch (AirisException e) {
                printMessage(e.getAirisMessage());
            }
        }
    }

    static void handleMark(Scanner input) throws AirisException {
        try {
            int index = input.nextInt();
            Task task = storage.get(index - 1);
            task.markAsDone();
            printMessage(String.format(doneMessage, task));
        } catch (NoSuchElementException e) {
            throw new AirisException("Index not found");
        } catch (IndexOutOfBoundsException e) {
            throw new AirisException("Index is out of bounds");
        }
        input.nextLine(); // Consume current line
    }

    static void handleUnmark(Scanner input) throws AirisException {
        try {
            int index = input.nextInt();
            Task task = storage.get(index - 1);
            task.markAsNotDone();
            printMessage(String.format(notDoneMessage, task));
        } catch (NoSuchElementException e) {
            throw new AirisException("Index not found");
        } catch (IndexOutOfBoundsException e) {
            throw new AirisException("Index is out of bounds");
        }
        input.nextLine(); // Consume current line
    }

    static void handleDelete(Scanner input) throws AirisException {
        try {
            int index = input.nextInt();
            Task task = storage.remove(index - 1);
            printMessage(String.format(deleteMessage, task));
        } catch (NoSuchElementException e) {
            throw new AirisException("Index not found");
        } catch (IndexOutOfBoundsException e) {
            throw new AirisException("Index is out of bounds");
        }
        input.nextLine();
    }

    static void handleTodo(Scanner input) throws AirisException {
        String information = input.nextLine().strip();
        String[] tokens = getTokens(information);
        if (tokens.length == 0) {
            throw new AirisException("Description of task cannot be empty");
        }
        String description = String.join(" ", tokens);
        Task task = new Todo(description);
        storage.add(task);
        printMessage("I've added this task to your list:\n\t" + task);
    }

    static void handleDeadline(Scanner input) throws AirisException {
        String information = input.nextLine().strip();
        String[] tokens = getTokens(information);

        ArrayList<String> descriptionList = new ArrayList<>();
        ArrayList<String> dueList = new ArrayList<>();
        ArrayList<String> current = descriptionList;

        for (String token : tokens) {
            if (token.equals("/by")) {
                current = dueList;
            } else {
                current.add(token);
            }
        }
        
        if (descriptionList.isEmpty()) {
            throw new AirisException("Description of the task cannot be empty");
        }
        if (dueList.isEmpty()) {
            throw new AirisException("Due date of the deadline cannot be empty");
        }
        
        String description = String.join(" ", descriptionList);
        String due = String.join(" ", dueList);
        
        Task task = new Deadline(description, due);
        storage.add(task);
        printMessage("I've added this task to your list:\n\t" + task);
    }

    static void handleEvent(Scanner input) throws AirisException {
        String information = input.nextLine().strip();
        String[] tokens = getTokens(information);

        ArrayList<String> descriptionList = new ArrayList<>();
        ArrayList<String> startList = new ArrayList<>();
        ArrayList<String> endList = new ArrayList<>();
        ArrayList<String> current = descriptionList;

        for (String token : tokens) {
            if (token.equals("/from")) {
                current = startList;
            } else if (token.equals("/to")) {
                current = endList;
            } else {
                current.add(token);
            }
        }

        if (descriptionList.isEmpty()) {
            throw new AirisException("Description of the task cannot be empty");
        }
        if (startList.isEmpty()) {
            throw new AirisException("Start time of the event cannot be empty");
        }
        if (endList.isEmpty()) {
            throw new AirisException("End time of the event cannot be empty");
        }

        String description = String.join(" ", descriptionList);
        String start = String.join(" ", startList);
        String end = String.join(" ", endList);

        Task task = new Event(description, start, end);
        storage.add(task);
        printMessage("I've added this task to your list:\n\t" + task);
    }

    static void quitProgram() {
        printMessage(byeMessage);
        System.exit(0);
    }

    static String[] getTokens(String information) {
        String[] tokens = information.split("\\s+");
        return Arrays.stream(tokens).filter(token -> !token.isEmpty()).toArray(String[]::new);
    }

    static String wrapMessage(String msg) {
        String hline = "_".repeat(50);
        return hline + "\n" + msg + "\n" + hline;
    }

    static void printMessage(String msg) {
        System.out.println(wrapMessage(msg));
    }
}
