package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.animations.Transition;
import sample.database.DatabaseHandler;
import sample.model.User;
import sample.tools.ConstViews;
import sample.tools.URLConversion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField signUpFirstName;

    @FXML
    private JFXTextField signUpLastName;

    @FXML
    private JFXTextField signUpUsername;

    @FXML
    private JFXTextField signUpLocation;

    @FXML
    private JFXCheckBox signUpCheckBoxMale;

    @FXML
    private JFXTextField signUpEmail;

    @FXML
    private JFXCheckBox signUpCheckBoxFemale;

    @FXML
    private JFXPasswordField signUpPassword;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private JFXButton backToLoginButton;

    @FXML
    private AnchorPane signUpAnchorPane;

    @FXML
    private MenuBar signupMenu;

    @FXML
    private MenuItem menuCloseButton;

    @FXML
    void initialize() {

        menuCloseButton.setOnAction(actionEvent -> {
            signupMenu.getScene().getWindow().hide();
        });

        signUpCheckBoxFemale.setOnAction(event -> {
            signUpCheckBoxMale.setSelected(false);
        });

        signUpCheckBoxMale.setOnAction(event -> {
            signUpCheckBoxFemale.setSelected(false);
        });

        signUpButton.setOnAction(event -> {
            if (createUser()) {
                clearUserOptions();
                openSignupCompleteDialog();
            } else {
                clearPassword();
                openUserNotCreatedDialog();
            }
        });

        backToLoginButton.setOnAction(event -> {
            showTheLoginScreen();
        });
    }

    private String getGender() {
        String gender = "";

        if (signUpCheckBoxFemale.isSelected()) {
            gender = "Female";
        } else if (signUpCheckBoxMale.isSelected()) {
            gender = "Male";
        }
        return gender;
    }

    //validating the newly created user
    private boolean createUser() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        User user = newUser();
        if (!user.getUserName().equals("") && !user.getPassword().equals("") && !user.getEmail().equals("")) {
            databaseHandler.signUpUser(user);
        } else {
            System.out.println("user was not created. returning false.");
            return false;
        }

        if (databaseHandler.isUserCreated()) {
            return true;
        }

        return false;
    }

    private User newUser() {
        String firstName = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpUsername.getText();
        String password = signUpPassword.getText();
        String location = signUpLocation.getText();
        String email = signUpEmail.getText();
        String gender = getGender();

        return new User(firstName, lastName, userName, location, gender, password, email);
    }

    private void showTheLoginScreen() {
        //take users to AddItem screen

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        fadeSignUpScreen(root);

        Stage stage = (Stage) signUpAnchorPane.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void openSignupCompleteDialog() {
        Dialog okDialog = new Dialog();

        okDialog.setTitle("Confirmation");
        okDialog.setHeaderText("User signup completed successfully!");
        okDialog.setContentText("Press OK to go to the login page!");

        String iconPath = "src/sample/assets/icons8-user-90.png";
        okDialog.setGraphic(URLConversion.dialogImage(iconPath));

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        okDialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        okDialog.showAndWait();

        if(okDialog.getResult() == okButton){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(ConstViews.LOGIN_SCREEN));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent parent = loader.getRoot();

            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setScene(new Scene(parent));
        }else{
            //just close the dialog
        }
    }

    private void openUserNotCreatedDialog() {
        Dialog errorDialog = new Dialog();

        errorDialog.setTitle("User not created");
        errorDialog.setHeaderText("Signup was not completed!");
        errorDialog.setContentText("Be sure to have the username, password and email filled in");
        errorDialog.setGraphic(URLConversion.dialogImage("src/sample/assets/icons8-delete-96.png"));

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);

        errorDialog.getDialogPane().getButtonTypes().add(okButton);

        errorDialog.showAndWait();
    }

    private void fadeSignUpScreen(Parent root) {
        Transition rootTransition = new Transition(root);
        rootTransition.setToFadeIn();
        rootTransition.setFadeTransition(1000);
        rootTransition.fade();
    }

    private void clearUserOptions() {
        signUpFirstName.setText("");
        signUpLastName.setText("");
        signUpEmail.setText("");
        signUpLocation.setText("");
        signUpPassword.setText("");
        signUpUsername.setText("");
        signUpCheckBoxFemale.setSelected(false);
        signUpCheckBoxMale.setSelected(false);
    }

    private void clearPassword() {
        signUpPassword.setText("");
    }

//
//    private void showFXMLForDialog(){
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/sample/view/signupcomplete.fxml"));
//
//        try {
//            loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Parent root = loader.getRoot();
//        Dialog dialog = new Dialog();
//        dialog.getDialogPane().setContent(root);
//        dialog.show();
//    }
}
