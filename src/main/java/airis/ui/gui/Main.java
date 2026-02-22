package airis.ui.gui;

import java.io.IOException;

import airis.Airis;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Main JavaFX class.
 */
public class Main extends Application {
    private Airis airis = new Airis();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setAiris(airis);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
