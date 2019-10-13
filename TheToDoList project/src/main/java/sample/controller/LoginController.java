package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import sample.animations.Shaker;
import sample.animations.Transition;
import sample.database.DatabaseHandler;
import sample.model.User;
import sample.tools.ConstViews;
import sample.tools.DisplayScreen;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    private int userId;

    private boolean isLoggedIn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXTextField loginUsername;

    @FXML
    private JFXPasswordField loginPassword;

    @FXML
    private JFXButton loginSignupButton;

    @FXML
    private Label enterYourCredentialsLabel;

    @FXML
    private MenuBar loginMenu;

    @FXML
    private MenuItem menuCloseButton;

    private DatabaseHandler databaseHandler;

    private DisplayScreen displayScreen;

    @FXML
    void initialize() {

        menuCloseButton.setOnAction(actionEvent -> {
            loginMenu.getScene().getWindow().hide();
        });
        displayScreen = new DisplayScreen();

        databaseHandler = new DatabaseHandler();

        //loginbutton as default button
        loginButton.setDefaultButton(true);

        loginButton.setOnAction(actionEvent -> {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    queryDatabaseForLogin();
                    Platform.runLater(()->{
                        showLoginResult();
                    });
                }
            };
            new Thread(task).start();
        });

        //when the signup button is pressed, we hide the other screen and we put on the signup page
        loginSignupButton.setOnAction(event -> {

            //take users to signup screen
            displayScreen.loadAndChangeDisplayScreen(loginSignupButton, ConstViews.SIGN_UP_SCREEN);
        });


    }

    private void queryDatabaseForLogin() {
        String loginText = loginUsername.getText().trim();
        String loginPwd = loginPassword.getText().trim();

        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPwd);

        ResultSet userRow = databaseHandler.getUser(user);
        int counter = 0;

        try {
            while (userRow != null && userRow.next()) {
                counter++;
                String name = userRow.getString("firstname");
                this.userId = userRow.getInt("userid");
                //print to console the current logged in user
                System.out.println("User that is logged in: Welcome user " + name + " ! -> userId: " + userId);
            }
            if (counter == 1) {
                isLoggedIn = true;
            } else {
                isLoggedIn = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showLoginResult() {
        if (isLoggedIn) {
            enterYourCredentialsLabel.setText("");
            showAddItemScreen();
        } else {
            Shaker shakerUsername = new Shaker(loginUsername);
            Shaker shakePassword = new Shaker(loginPassword);
            shakerUsername.shake();
            shakePassword.shake();
            enterYourCredentialsLabel.setText("Please enter your credentials");
            Shaker shakerLabelCredentials = new Shaker(enterYourCredentialsLabel);
            shakerLabelCredentials.shake();
        }
    }

    private void showAddItemScreen() {
        //take users to AddItem screen
        boolean hasTasks = false;

        try {
            int currentUserTaskCount = databaseHandler.getAllTasks(userId);
            if(currentUserTaskCount > 0){
                hasTasks = true;
            }
        } catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        }



        //sets the user id


        if(!hasTasks){
            displayScreen.loadAndChangeDisplayScreen(loginButton,ConstViews.ADD_ITEM_SCREEN);
            setUserId(displayScreen);
        }else{
            setUserId(userId);
            displayScreen.loadAndChangeDisplayScreen(loginButton,ConstViews.ADD_ITEM_FORM_SCREEN);
        }
        fadeLoginScreen(displayScreen.getRoot());
    }

    private void fadeLoginScreen(Parent root) {
        Transition rootTransition = new Transition(root);
        rootTransition.fade(750, true);
    }

    private void setUserId(DisplayScreen displayScreen){
        AddItemController addItemController = displayScreen.getLoader().getController();
        addItemController.setUserId(userId);
    }

    private void setUserId(int userId) {
        AddItemController.userId = userId;
//        System.out.println("User Id from AddItemController is: " + this.userId);
    }
}
