package tasks;

public class Task {
    int idTask = 0;
    String name;
    String description;
    String status;

    public Task(String name, String descriptionTask, String status) {

        this.name = name;
        this.status = status;
        this.description = descriptionTask;
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

    public String getStatus() {
        return status;
    }

}
