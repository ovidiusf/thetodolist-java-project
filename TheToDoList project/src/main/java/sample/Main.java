package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //launching the program normaly
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/addItem.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/list.fxml"));

        primaryStage.setTitle("The To Do List");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
