
public class Task extends Epic {

    int keyEpic;

    public Task(String name, String descriptionTask, String newStatusTask, int keyEpic) {
        super(name, descriptionTask, newStatusTask);
        this.keyEpic = keyEpic;
    }

    public String getStatusTask(){

        return status;
    }

    public int getKeyEpic() {
        return keyEpic;
    }
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", keyEpic='" + keyEpic + '\'' +
                '}';
    }
}





