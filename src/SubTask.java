

public class SubTask extends Epic{

    int keyTask;


    public SubTask(String name, String descriptionTask, String newStatusTask, int keyTask) {
        super(name, descriptionTask, newStatusTask);
        this.keyTask = keyTask;

    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", keyTask='" + keyTask + '\'' +
                '}';
    }

    public String getStatusSubTask(){

        return status;
    }

    public int getKeyTask() {
        return keyTask;
    }
}