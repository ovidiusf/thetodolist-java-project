package sample.tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.animations.Transition;

import java.io.IOException;

public class DisplayScreen {
    private FXMLLoader loader;
    private Stage stage;


    public DisplayScreen() {
        this.loader = new FXMLLoader();
        this.stage = new Stage();
    }

    /**
     * load and change the display with the desired one, based on the current node
     * @param pathname -> contains the path to the FXML view
     * @param node -> can be for example a JFXButton or a button
     * or anything else that is on the current scene
     * can even be an anchor pane
     */

    public void loadAndChangeDisplayScreen(Node node, String pathname) {
        loader.setLocation(getClass().getResource(pathname));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(getRoot()));
    }

    public Parent getRoot() {
        return loader.getRoot();
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void changeAndAnimateScreen(AnchorPane anchorPane, String pathName){
        try {
            AnchorPane formPane = FXMLLoader.load(getClass().getResource(pathName));

            Transition rootTransition = new Transition(formPane);
            rootTransition.fade(1000, true);
            anchorPane.getChildren().setAll(formPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createInvalidDueDateWarningWindow() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Due Date was set in past!");
        alert.setContentText("Please select a valid due date!");
        alert.showAndWait();
    }
}
