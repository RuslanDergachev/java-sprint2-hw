package tasks;

public class SubTask extends Task {

    int keyEpic;


    public SubTask(String name, String descriptionTask, String newStatusTask, int keyEpic) {
        super(name, descriptionTask, newStatusTask);
        this.keyEpic = keyEpic;

    }

    @Override
    public String toString() {
        return "tasks.SubTask{" +
                "id:" + idTask + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", keyEpic='" + keyEpic + '\'' +
                '}';
    }

    public int getKey() {
        return keyEpic;
    }
}