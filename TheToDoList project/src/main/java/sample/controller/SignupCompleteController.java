package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupCompleteController {

    @FXML
    private JFXButton signUpCompleteButton;

    @FXML
    private VBox dialogBoxSignupComplete;

    @FXML
    void initialize() {
        signUpCompleteButton.setOnAction(event->{
            Stage currentStage = (Stage) dialogBoxSignupComplete.getScene().getWindow();
            currentStage.close();
        });
    }
}
