package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.tools.DateChecker;
import sample.tools.DisplayScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CellController extends JFXListCell<Task> {

    @FXML
    private ImageView updateButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label taskLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteButton;

    @FXML
    private Label dueDateLabel;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);

        if (empty || myTask == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().
                        getResource("/view/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //task label and description label setting
            taskLabel.setText(myTask.getTask());
            descriptionLabel.setText(myTask.getDescription());
            dueDateLabel.setText(myTask.getDuedate().toString());


            //setting datecreated Label and due Date Label
            String dateLabelString = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(myTask.getDateCreated());
            String dueDateLabelString = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(myTask.getDuedate());

            //update the date and due date labels
            dateLabel.setText(dateLabelString);
            dueDateLabel.setText(dueDateLabelString);


            int taskId = myTask.getTaskId();

            //this will remove the clicked item
            deleteButton.setOnMouseClicked(event -> {
                databaseHandler = new DatabaseHandler();
                getListView().getItems().remove(getItem());
                try {
                    databaseHandler.deleteTask(AddItemController.userId, taskId);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            setText(null);
            setGraphic(rootAnchorPane);

            updateButton.setOnMouseClicked(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/updateTaskForm.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                Parent root = loader.getRoot();
                stage.setScene(new Scene(root));
                stage.show();

                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setUpdateTaskField(myTask.getTask());
                updateTaskController.setUpdateDescriptionField(myTask.getDescription());
                updateTaskController.setUpdateDatePicker(myTask.getDuedate());
                updateTaskController.setUpdateTimePicker(myTask.getDuedate());

                updateTaskController.getUpdateTaskButton().setOnAction(event1 -> {
                    Calendar calendar = Calendar.getInstance();

                    java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
                    databaseHandler = new DatabaseHandler();
                    try {
                        System.out.println("taskid " + myTask.getTaskId());
                        System.out.println("userId is " + AddItemController.userId);

                        Timestamp dueDateTimestamp = new Timestamp(0);
//                                Timestamp.valueOf(updateTaskController.getUpdateDatePicker().atStartOfDay());

                        int day = updateTaskController.getUpdateDatePicker().getDayOfMonth();
                        int month = updateTaskController.getUpdateDatePicker().getMonthValue()-1;
                        int year = updateTaskController.getUpdateDatePicker().getYear();
                        int hour = updateTaskController.getUpdateTimePicker().getHour();
                        int minutes = updateTaskController.getUpdateTimePicker().getMinute();

                        Calendar cTs = Calendar.getInstance();
//                        cTs.setTimeInMillis(timestamp.getTime());
                        cTs.set(Calendar.HOUR_OF_DAY, hour);
                        cTs.set(Calendar.MINUTE, minutes);
                        cTs.set(Calendar.MONTH, month);
                        cTs.set(Calendar.DAY_OF_MONTH, day);
                        cTs.set(Calendar.YEAR, year);
                        dueDateTimestamp.setTime(cTs.getTimeInMillis());

                        Calendar dueDateCalendar = Calendar.getInstance();

                        Timestamp currentTimestamp = new java.sql.Timestamp (dueDateCalendar.getTimeInMillis());

                        boolean currentDay = DateChecker.checkCurrentDay(month, day, year);
//                        System.out.println(DateChecker.isBehindCurrentDay(dueDateTimestamp, currentTimestamp));
//                        System.out.println(DateChecker.isBehindCurrentHour(hour, minutes, currentDay));
                        if(DateChecker.isBehindCurrentDay(dueDateTimestamp, currentTimestamp) || DateChecker.isBehindCurrentHour(hour, minutes, currentDay)){
                            DisplayScreen.createInvalidDueDateWarningWindow();
                        }else{
                            databaseHandler.updateTask(timestamp, dueDateTimestamp, updateTaskController.getUpdateTaskField(), updateTaskController.getUpdateDescriptionField(), AddItemController.userId,
                                    myTask.getTaskId());
                            stage.close();
                        }
//                        updateTaskController.refreshTheCellList();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            });
        }
    }

}
