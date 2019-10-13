package sample.model;

import java.sql.Timestamp;

public class Task {
    private Timestamp dateCreated;
    private String description;
    private String task;
    private int taskId;
    private int userid;
    private Timestamp duedate;

    public Task() {
    }

    public Task(Timestamp dateCreated, String description, String task, Timestamp duedate) {
        this.dateCreated = dateCreated;
        this.description = description;
        this.task = task;
        this.duedate = duedate;
    }

    public Timestamp getDuedate() {
        return duedate;
    }

    public void setDueDate(Timestamp duedate) {
        this.duedate = duedate;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


}
