package tasks;

import managerTasks.StatusTask;
import managerTasks.TypeTask;

public class SubTask extends Task {

    protected int keyEpic;

    public SubTask(String name, String descriptionTask, StatusTask newStatusTask, int keyEpic) {
        super(name, descriptionTask, newStatusTask);
        this.keyEpic = keyEpic;
    }

    public SubTask(int taskId, String name, String description, StatusTask status, int keyEpic) {
        super(taskId, name, description, status);
        this.keyEpic = keyEpic;
    }

    @Override
    public String toString() {
        return "tasks.SubTask{" +
                "id:" + taskId + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", keyEpic='" + keyEpic + '\'' +
                '}';
    }

    public int getKey() {
        return keyEpic;
    }

    @Override
    public TypeTask getType() {
        return TypeTask.SUBTASK;
    }

    @Override
    public String toCSV() {
        return super.toCSV() + "," + getKey();
    }
}