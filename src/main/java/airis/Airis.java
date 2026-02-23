package airis;

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

    private UI ui;
    private Parser parser;
    private TaskList taskList;
    private Storage storage;

    public Airis() {
        this(new TextUI());
    }

    /**
     * Construct an Airis instance with a specific UI.
     * @param ui
     */
    public Airis(UI ui) {
        this.ui = ui;
        initialize();
        ui.display(helloMessage);
    }

    /**
     * Initialize the chatbot.
     */
    public void initialize() {
        this.parser = Parser.makeFullParser();
        this.taskList = new TaskList();

        this.storage = Storage.localFile("data.txt");
        try {
            storage.createIfNotExists();
            storage.load(taskList);
        } catch (AirisException e) {
            System.out.println(e.getAirisMessage());
        }
    }

    /**
     * Process the command provided by the text.
     * @param line The command text.
     */
    public void processCommand(String line) {
        try {
            Command cmd = this.parser.parse(line);
            Response response = cmd.process(this.storage, this.taskList);
            response.process(this.ui);
        } catch (AirisException e) {
            this.ui.display(e.getAirisMessage());
        }
    }

    /**
     * Dummy method to test JavaFX.
     *
     * @param message Message to be echoed.
     * @return The echoed message.
     */
    public String echo(String message) {
        return "Echo: " + message;
    }
}
