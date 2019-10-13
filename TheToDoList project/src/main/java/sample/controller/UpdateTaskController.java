package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateTaskController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane updateItemPane;

    @FXML
    private JFXTextField updateTaskField;

    @FXML
    private JFXTextField updateDescriptionField;

    @FXML
    private JFXButton updateTaskButton;

    @FXML
    private JFXDatePicker updateDatePicker;

    @FXML
    private JFXTimePicker updateTimePicker;

    @FXML
    void initialize() {

    }

    public void setUpdateTaskField(String task) {
        this.updateTaskField.setText(task);
    }

    public void setUpdateDescriptionField(String descriptionField) {
        this.updateDescriptionField.setText(descriptionField);
    }

    public JFXButton getUpdateTaskButton() {
        return this.updateTaskButton;
    }

    public String getUpdateTaskField() {
        return this.updateTaskField.getText().trim();
    }

    public String getUpdateDescriptionField() {
        return this.updateDescriptionField.getText().trim();
    }

    public void setUpdateDatePicker(Timestamp timestampUpdateDatePicker) {
        LocalDate localDate = timestampUpdateDatePicker.toLocalDateTime().toLocalDate();
        this.updateDatePicker.setValue(localDate);
    }

    public void setUpdateTimePicker(Timestamp timestampUpdateTimePicker) {
        LocalTime localTime = timestampUpdateTimePicker.toLocalDateTime().toLocalTime();
        this.updateTimePicker.setValue(localTime);
    }

    public LocalDate getUpdateDatePicker() {
        return updateDatePicker.getValue();
    }

    public LocalTime getUpdateTimePicker() {
        return updateTimePicker.getValue();
    }


    public void refreshTheCellList() throws SQLException {
        System.out.println("Calling refresh list");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/list.fxml"));

        try{
            loader.load();
            ListController listController = loader.getController();
            listController.refreshList();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
