package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.animations.Shaker;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.tools.ConstViews;
import sample.tools.DateChecker;
import sample.tools.DisplayScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    private DatabaseHandler databaseHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField taskField;

    @FXML
    private JFXTextField descriptionField;

    @FXML
    private JFXButton saveTaskButton;

    @FXML
    private Label successLabel;

    @FXML
    private JFXButton myToDosButton;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXButton logoutAddItemForm;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDatePicker addItemDatePicker;

    @FXML
    private JFXTimePicker addItemTimePicker;

    @FXML
    private MenuBar addItemFormMenu;

    @FXML
    private MenuItem seeAllItemsMenuButton;

    @FXML
    private MenuItem logoutMenuButton;

    @FXML
    private MenuItem menuCloseButton;

    @FXML
    void initialize() {

        addItemDatePicker.setValue(LocalDate.now());

        databaseHandler = new DatabaseHandler();

        logoutAddItemForm.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.LOGIN_SCREEN);
        });

        menuCloseButton.setOnAction(actionEvent -> {
            addItemFormMenu.getScene().getWindow().hide();
        });

        logoutMenuButton.setOnAction(actionEvent -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.LOGIN_SCREEN);
        });

        seeAllItemsMenuButton.setOnAction(actionEvent -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.VIEW_ALL_ITEMS);
        });

        myToDosButton.setVisible(true);

        myToDosButton.setText("My to do items: (" + numberOfTasks(databaseHandler) + ")");

        saveTaskButton.setOnAction(event -> {
            boolean addedTask;
            if (saveTaskButtonPressed(databaseHandler)) {
                taskField.setText("");

                descriptionField.setText("");

                addItemDatePicker.getEditor().clear();
                addItemTimePicker.getEditor().clear();
                addedTask = true;
            }else{
                addedTask = false;
            }
            showSuccessOrErrorLabel(addedTask);
        });

        myToDosButton.setOnAction(actionEvent -> {

//                createNewThreadForListView();
            //send users to the list screen
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/view/list.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
//                Stage stage = new Stage();
            Stage stage = (Stage) myToDosButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        });
    }

    private boolean saveTaskButtonPressed(DatabaseHandler databaseHandler) {
        Task task = new Task();

        String taskText = taskField.getText().trim();
        String taskDescription = descriptionField.getText().trim();

        if (!taskText.equals("") || !taskDescription.equals("")) {

//          we get the user id from here
//          System.out.println("User id from AddItemFormController: "
//          + AddItemController.userId);
            task.setUserid(AddItemController.userId);
            task.setDateCreated(this.getCurrentTimeStamp());
            task.setDescription(taskDescription);
            task.setTask(taskText);

            //create a timestamp with selectable date and hour
            Timestamp timestamp = Timestamp.valueOf(addItemDatePicker.getValue().atStartOfDay());

            int hour = addItemTimePicker.getValue().getHour();
            int minutes = addItemTimePicker.getValue().getMinute();

            int day = addItemDatePicker.getValue().getDayOfMonth();
            int month = addItemDatePicker.getValue().getMonthValue() - 1;
            int year = addItemDatePicker.getValue().getYear();

            Calendar cTs = Calendar.getInstance();

            cTs.set(Calendar.HOUR_OF_DAY, hour);
            cTs.set(Calendar.MINUTE, minutes);
            cTs.set(Calendar.MONTH, month);
            cTs.set(Calendar.DAY_OF_MONTH, day);
            cTs.set(Calendar.YEAR, year);

            timestamp.setTime(cTs.getTimeInMillis());

            task.setDueDate(timestamp);

            boolean currentDay = DateChecker.checkCurrentDay(month, day, year);

            if (validateDateForTask() && validateTimeForTask(hour, minutes, currentDay)) {
                //insert the task into the DB, if everything is ok : texts not null
                databaseHandler.insertTask(task);
                return true;
            } else {
                createInvalidDueDateWarningWindow();
                addItemTimePicker.setValue(LocalTime.now());
                addItemDatePicker.setValue(LocalDate.now());
                return false;
            }
        } else {
            System.out.println("Nothing added!");
            return false;
        }
    }

    private Timestamp getCurrentTimeStamp() {
        //return the current time stamped to be used when creating tasks

        Calendar calendar = Calendar.getInstance();

        return new java.sql.Timestamp(calendar.getTimeInMillis());
    }

    private int numberOfTasks(DatabaseHandler databaseHandler) {
        int taskNumber = 0;
        try {
            taskNumber = databaseHandler.getAllTasks(AddItemController.userId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taskNumber;
    }

    private void showSuccessOrErrorLabel(boolean addedTask) {
        errorLabel.setVisible(false);
        successLabel.setVisible(false);

        if (addedTask) {
            successLabel.setVisible(true);
        } else {
            errorLabel.setVisible(true);
            Shaker shaker = new Shaker(errorLabel);
            shaker.shake();
        }
    }

    private boolean validateDateForTask() {
        Timestamp timestamp = Timestamp.valueOf(addItemDatePicker.getValue().atStartOfDay());
        if (DateChecker.isBehindCurrentDay(timestamp, getCurrentTimeStamp())) {
            return false;
        }
        return true;
    }

    private boolean validateTimeForTask(int hour, int minutes, boolean currentDay) {
        if (DateChecker.isBehindCurrentHour(hour, minutes, currentDay)) {
            return false;
        }
        return true;
    }

    private void createInvalidDueDateWarningWindow() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Due Date was set in past!");
        alert.setContentText("Please select a valid due date!");
        alert.showAndWait();
    }

//
//    private void createNewThreadForListView() {
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1);
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            //call the method to hide the old Scene
//                            saveTaskButton.getScene().getWindow().hide();
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        new Thread(task).start();
//    }
}
