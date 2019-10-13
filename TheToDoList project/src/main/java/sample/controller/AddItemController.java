package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.animations.ButtonShaker;
import sample.animations.Transition;
import sample.tools.ConstViews;
import sample.tools.DisplayScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    public static int userId;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private JFXButton logoutAddItem;

    @FXML
    private URL location;

    @FXML
    private ImageView addButton;

    @FXML
    private Label noTaskLabel;

    @FXML
    private MenuBar addItemMenu;

    @FXML
    private MenuItem addItemButton;

    @FXML
    private MenuItem seeAllItemsMenuButton;

    @FXML
    private MenuItem logoutMenuButton;

    @FXML
    private MenuItem menuCloseButton;

    @FXML
    void initialize() {

        menuCloseButton.setOnAction(actionEvent -> {
            addItemMenu.getScene().getWindow().hide();
        });

        logoutMenuButton.setOnAction(actionEvent -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.LOGIN_SCREEN);
        });

        seeAllItemsMenuButton.setOnAction(actionEvent -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.VIEW_ALL_ITEMS);
        });

        addItemButton.setOnAction(actionEvent -> {
            AddItemController.userId = getUserId();
            fadeAddItemScene();
        });

        logoutAddItem.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.LOGIN_SCREEN);
        });

        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ButtonShaker addButtonShaker = new ButtonShaker(addButton);
            addButtonShaker.shake();

            //we set the userid from the additemformcontroller to the user id from the additemcontroller
            AddItemController.userId = getUserId();

            //remove the button and the label (no tasks today, add task)
            fadeAddButton();
            fadeLabel();

            //Fade the older scene into the new scene
            fadeAddItemScene();
        });

    }

    private void fadeLabel() {
        Transition fadeLabel = new Transition(noTaskLabel);
        noTaskLabel.relocate(0, 85);
        noTaskLabel.setOpacity(0);
        fadeLabel.fade();
    }

    private void fadeAddButton() {
        Transition fadeAddButton = new Transition(addButton);
        addButton.setFitHeight(70);
        addButton.setFitWidth(70);
        addButton.relocate(0, 20);
        addButton.setOpacity(0);
        fadeAddButton.fade();
    }

    /**
     * method to fade the old anchorpane with the addItem screen (no tasks) into the one with adding tasks
     * contains Platform.runLater and Threads, in order to allow the buttons to fade off
     * puts thread to sleep for a period of time (500 milis for ex. ) in order for the animations to show (noTaskLabel and addItemButton)
     * after this period of time the addItemForm scene appears fading in
     */

    private void fadeAddItemScene() {
        //create a new Thread for JavaFX
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //call the method to fade the old Scene
                            fadeAnchorPane();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(task).start();
    }

    private void fadeAnchorPane() {
        try {
            AnchorPane formPane = FXMLLoader.load(getClass().
                    getResource("/view/addItemForm.fxml"));

            Transition rootTransition = new Transition(formPane);
            rootTransition.fade(2000, true);
            rootPane.getChildren().setAll(formPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //AdditemController setter and getter, static variable userId

    public void setUserId(int userId) {
        this.userId = userId;
//        System.out.println("User Id from AddItemController is: " + this.userId);
    }

    public int getUserId() {
        return this.userId;
    }

}



