package airis;

import java.util.Scanner;

import airis.command.Command;
import airis.command.Parser;
import airis.command.Response;
import airis.storage.Storage;
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

    /**
     * This is the main function
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        UI ui = new TextUI();
        Parser parser = Parser.makeDefaultParser();
        TaskList taskList = new TaskList();

        Storage storage = Storage.localFile("data.txt");
        try {
            storage.createIfNotExists();
            storage.load(taskList);
        } catch (AirisException e) {
            ui.display(e.getAirisMessage());
        }

        ui.display(helloMessage);

        Scanner stdin = new Scanner(System.in);
        while (true) {
            String line = stdin.nextLine();
            try {
                Command cmd = parser.parse(line);
                Response response = cmd.process(storage, taskList);
                response.process(ui);
            } catch (AirisException e) {
                ui.display(e.getAirisMessage());
            }
        }
    }
}
