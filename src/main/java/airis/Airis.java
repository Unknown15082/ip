package airis;

import java.util.Scanner;

import airis.command.Command;
import airis.command.Parser;
import airis.command.Response;
import airis.task.TaskList;
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

    /**
     * This is the main function
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Storage storage = new Storage();
        UI ui = new TextUI();
        Parser parser = Parser.makeDefaultParser();
        TaskList taskList = new TaskList();

        ui.display(helloMessage);

        Scanner stdin = new Scanner(System.in);
        while (true) {
            String line = stdin.nextLine();
            try {
                Command cmd = parser.parse(line);
                Response response = cmd.process(taskList);
                response.process(ui);
            } catch (AirisException e) {
                ui.display(e.getAirisMessage());
            }
        }
    }
}
