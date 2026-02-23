package airis.ui.gui;

import airis.Airis;
import airis.ui.UI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Main window controller.
 */
public class MainWindow extends AnchorPane implements UI {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Airis airis;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/swarm.png"));
    private Image airisImage = new Image(this.getClass().getResourceAsStream("/images/heart.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setAiris() {
        this.airis = new Airis(this);
    }

    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        userInput.clear();
        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(userText, userImage)
        );

        airis.processCommand(userText);
    }

    @Override
    public void display(String message) {
        dialogContainer.getChildren().add(DialogBox.getAirisDialog(message, airisImage));
    }
}
