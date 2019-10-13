package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.tools.ConstViews;
import sample.tools.DisplayScreen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class ListController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView refreshListButton;

    @FXML
    private JFXListView<Task> listTasks;

    @FXML
    private JFXButton listSaveTaskButton;

    @FXML
    private JFXTextField listDescriptionField;

    @FXML
    private JFXTextField listTaskField;

    @FXML
    private JFXButton logoutButtonList;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;

    private DatabaseHandler databaseHandler;

    @FXML
    private JFXButton backAddItemScreenButton;

    @FXML
    void initialize() throws SQLException {
//        listTasks.setItems(listView);
//
//        listTasks.setCellFactory(param -> new JFXCell());

        tasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();

        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDateCreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            task.setDueDate(resultSet.getTimestamp("duedate"));

            tasks.addAll(task);
        }

        listTasks.setItems(tasks);

        listTasks.setCellFactory(CellController -> new CellController());

        listSaveTaskButton.setOnAction(event -> {
            addNewTask();
        });

        logoutButtonList.setOnAction(event -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.LOGIN_SCREEN);
        });

        refreshListButton.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        backAddItemScreenButton.setOnAction(actionEvent -> {
            DisplayScreen displayScreen = new DisplayScreen();
            displayScreen.changeAndAnimateScreen(rootPane, ConstViews.ADD_ITEM_FORM_SCREEN);
        });
    }

    private void addNewTask() {
        if (!listTaskField.getText().equals("") || !listDescriptionField.getText().equals("")) {
            Task myNewTask = new Task();
            myNewTask.setUserid(AddItemController.userId);
            myNewTask.setTask(listTaskField.getText().trim());
            myNewTask.setDescription(listDescriptionField.getText().trim());
            myNewTask.setDateCreated(getCurrentTimeStamp());
            myNewTask.setDueDate(getCurrentTimeStamp());
            databaseHandler.insertTask(myNewTask);

            listTaskField.setText("");
            listDescriptionField.setText("");

            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Timestamp getCurrentTimeStamp() {

        Calendar calendar = Calendar.getInstance();

        return new java.sql.Timestamp(calendar.getTimeInMillis());
    }

    public void refreshList() throws SQLException {
        refreshedTasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();

        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDateCreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            task.setDueDate(resultSet.getTimestamp("duedate"));

            refreshedTasks.addAll(task);
        }

        listTasks.setItems(refreshedTasks);
        listTasks.setCellFactory(CellController -> new CellController());
    }

    /**
     * static class JFXCell for updating cell content
     */

//    static class JFXCell extends JFXListCell<String> {
//        //HBox = Horizontal box
//        HBox hBox = new HBox();
//        Button helloButton = new Button("Hello");
//        Label task = new Label();
//
//        Pane pane = new Pane();
//
//        File file = new File(
//                "src/sample/assets/baseline_note_add_black_48dp.png");
//
//        Image icon = new Image(getCellResourceLocation(file));
//
//        ImageView iconImg = new ImageView(icon);
//
//
//        public JFXCell() {
//            super();
//
//            hBox.getChildren().addAll(iconImg, task, helloButton);
//            hBox.setHgrow(pane, Priority.ALWAYS);
//            iconImg.setFitWidth(70);
//            iconImg.setFitHeight(70);
//        }
//
//        public void updateItem(String taskName, boolean empty) {
//            super.updateItem(taskName, empty);
//            setText(null);
//            setGraphic(null);
//
//            if (taskName != null && !empty) {
//                task.setText(taskName);
//                setGraphic(hBox);
//            }
//        }
//
//        //method to convert the File's received location to a URL,
//        //in order to be able to call it using the Image class
//        //and display it in the Cell
//
//        private String getCellResourceLocation(File file) {
//            String localUrl = "";
//
//            try {
//                localUrl = file.toURI().toURL().toString();
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            return localUrl;
//        }
//    }
}


