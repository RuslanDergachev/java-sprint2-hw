package tasks;

import managerTasks.StatusTask;
import managerTasks.TypeTask;

public class Task {
    int taskId;
    protected String name;
    protected String description;
    protected StatusTask status;

    public Task(String name, String descriptionTask, StatusTask status) {
        this.name = name;
        this.status = status;
        this.description = descriptionTask;
    }

    public Task(int taskId, String name, String description, StatusTask status) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setId(int id) {
        taskId = id;
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id:" + taskId + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public StatusTask getStatus() {
        return status;
    }

    public TypeTask getType() {
        return TypeTask.TASK;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toCSV() {
        return getTaskId() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription();
    }
}
