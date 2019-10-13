package sample.database;

import sample.model.Task;
import sample.model.User;
import java.sql.*;

public class DatabaseHandler extends Configs {
    private Connection dbConnection;
//    private DataSource dataSource;
    private boolean userCreated;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
//        String connectionStringMySQL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/";
//
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setUser(dbName);
//        dataSource.setPassword(dbPass);
//        dataSource.setServerName(connectionString);
//        dataSource.setURL(connectionStringMySQL);

//        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
//        dbConnection = dataSource.getConnection();
        return dbConnection;
    }
    //Write

    public void signUpUser(User user) {

        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + ","
                + Const.USERS_PASSWORD + "," + Const.USERS_LOCATION + "," + Const.USERS_EMAIL + "," + Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getGender());

            preparedStatement.executeUpdate();
            userCreated = true;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            userCreated = false;
        }
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public ResultSet getTasksByUser(int userId) {
        ResultSet resultTasks = null;

        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " +
                Const.USERS_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userId);

            resultTasks = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultTasks;
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        if (!user.getUserName().equals("") || !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                    Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD + "=?";
            //select all from users where username ="paulo" and password="password"

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User tried to login with incorrect credentials");
        }
        return resultSet;
    }

    public void insertTask(Task task) {
        String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" + Const.USERS_ID + ","
                + Const.TASKS_DATE + "," + Const.TASKS_DESCRIPTION + "," + Const.TASKS_TASK + ", " + Const.TASKS_DUEDATE +") "
                + "VALUES(?,?,?,?,?)";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert)) {

            preparedStatement.setInt(1, task.getUserid());
            preparedStatement.setTimestamp(2, task.getDateCreated());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getTask());
            preparedStatement.setTimestamp(5, task.getDuedate());

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Read

    public int getAllTasks(int userId) throws SQLException, ClassNotFoundException {
//        SELECT COUNT(*) FROM todo.tasks WHERE userid ='1';
        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " +
                Const.USERS_ID + "=?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    //Update

    public void updateTask(Timestamp dateCreated, Timestamp dueDate, String taskText, String taskDescription, int userId, int taskId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE " + Const.TASKS_TABLE + " SET " + Const.TASKS_TASK + "=?, " + Const.TASKS_DESCRIPTION + "=?, " + Const.TASKS_DATE + "=?, " + Const.TASKS_DUEDATE + "=? WHERE " + Const.USERS_ID +
                "=?" + " AND " + Const.TASKS_ID + "=?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setString(1, taskText);
        preparedStatement.setString(2, taskDescription);
        preparedStatement.setTimestamp(3, dateCreated);
        preparedStatement.setTimestamp(4, dueDate);
        preparedStatement.setInt(5, userId);
        preparedStatement.setInt(6, taskId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    //Delete

    public void deleteTask(int userId, int taskId) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID +
                "=?" + " AND " + Const.TASKS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, taskId);
        preparedStatement.execute();
        preparedStatement.close();
    }


}

