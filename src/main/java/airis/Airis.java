package airis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import airis.command.Command;
import airis.command.Parser;
import airis.command.Response;
import airis.task.Deadline;
import airis.task.Event;
import airis.task.Task;
import airis.task.Todo;
import airis.ui.TextUI;
import airis.ui.UI;

/**
 * The main chatbot class.
 */
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

    private static Storage storage;
    private static UI ui;
    private static Parser parser;

    /**
     * This is the main function
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        storage = new Storage();
        ui = new TextUI();
        parser = Parser.makeDefaultParser();

        ui.display(helloMessage);

        Scanner stdin = new Scanner(System.in);
        while (true) {
            String line = stdin.nextLine();
            try {
                Command cmd = parser.parse(line);
                Response response = cmd.process();
                response.process(ui);
            } catch (AirisException e) {
                ui.display(e.getAirisMessage());
                break;
            }
        }
    }

    /**
     * Mark a task as completed.
     *
     * @param input The Scanner object
     * @throws AirisException if element is not found
     */
    static void handleMark(Scanner input) throws AirisException {
        try {
            int index = input.nextInt();
            Task task = storage.get(index - 1);
            task.markAsDone();
            ui.display(String.format(doneMessage, task));
        } catch (NoSuchElementException e) {
            throw new AirisException("Index not found");
        } catch (IndexOutOfBoundsException e) {
            throw new AirisException("Index is out of bounds");
        }
        input.nextLine(); // Consume current line
    }

    /**
     * Mark a task as not completed
     *
     * @param input The Scanner object
     * @throws AirisException if element is not found
     */
    static void handleUnmark(Scanner input) throws AirisException {
        try {
            int index = input.nextInt();
            Task task = storage.get(index - 1);
            task.markAsNotDone();
            ui.display(String.format(notDoneMessage, task));
        } catch (NoSuchElementException e) {
            throw new AirisException("Index not found");
        } catch (IndexOutOfBoundsException e) {
            throw new AirisException("Index is out of bounds");
        }
        input.nextLine(); // Consume current line
    }

    /**
     * Delete a task
     *
     * @param input The Scanner object
     * @throws AirisException if element is not found
     */
    static void handleDelete(Scanner input) throws AirisException {
        try {
            int index = input.nextInt();
            Task task = storage.remove(index - 1);
            ui.display(String.format(deleteMessage, task));
        } catch (NoSuchElementException e) {
            throw new AirisException("Index not found");
        } catch (IndexOutOfBoundsException e) {
            throw new AirisException("Index is out of bounds");
        }
        input.nextLine();
    }

    /**
     * Add a to-do task type
     *
     * @param input The Scanner object
     * @throws AirisException if description is empty
     */
    static void handleTodo(Scanner input) throws AirisException {
        String information = input.nextLine().strip();
        String[] tokens = getTokens(information);
        if (tokens.length == 0) {
            throw new AirisException("Description of task cannot be empty");
        }
        String description = String.join(" ", tokens);
        Task task = new Todo(description);
        storage.add(task);
        ui.display("I've added this task to your list:\n\t" + task);
    }

    /**
     * Add a deadline task type
     *
     * @param input The Scanner object
     * @throws AirisException if any field is empty
     */
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
        ui.display("I've added this task to your list:\n\t" + task);
    }

    /**
     * Add an event task type
     *
     * @param input The Scanner object
     * @throws AirisException if any field is empty
     */
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
        ui.display("I've added this task to your list:\n\t" + task);
    }

    static void handleFind(Scanner input) throws AirisException {
        try {
            String keyword = input.nextLine().strip();
            Task[] matches = storage.search(keyword);

            StringBuilder result = new StringBuilder();
            result.append("Here are the matching tasks in your list:");
            for (Task match : matches) {
                result.append(String.format("%n%s", match));
            }
            ui.display(result.toString());
        } catch (NoSuchElementException e) {
            throw new AirisException("No keyword was found.");
        }
    }

    /**
     * Display a goodbye message, then quit the program.
     */
    static void quitProgram() {
        ui.display(byeMessage);
        System.exit(0);
    }

    /**
     * Split the information text into tokens, separated by whitespaces
     *
     * @param information The information text
     * @return The list of tokens
     */
    static String[] getTokens(String information) {
        String[] tokens = information.split("\\s+");
        return Arrays.stream(tokens).filter(token -> !token.isEmpty()).toArray(String[]::new);
    }
}
