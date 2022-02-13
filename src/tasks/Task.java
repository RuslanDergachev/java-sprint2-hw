package tasks;
import managerTasks.StatusTask;
public class Task {
    int idTask = 0;
    String name;
    String description;
    protected StatusTask status;

    public Task(String name, String descriptionTask, StatusTask status) {

        this.name = name;
        this.status = status;
        this.description = descriptionTask;
    }

    public StatusTask getStatusTask() {
        return status;
    }

    public void setId(int id) {
        idTask = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id:" + idTask + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public StatusTask getStatus() {
        return status;
    }

}
