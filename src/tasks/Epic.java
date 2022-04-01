package tasks;

import managerTasks.StatusTask;
import managerTasks.TypeTask;

public class Epic extends Task {

    public Epic(String name, String descriptionTask, StatusTask newStatusTask) {
        super(name, descriptionTask, newStatusTask);
    }

    public Epic(int taskId, String name, String description, StatusTask status) {
        super(taskId, name, description, status);
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "id:" + taskId + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public TypeTask getType() {
        return TypeTask.EPIC;
    }

    @Override
    public String getName() {
        return super.getName();
    }
}





