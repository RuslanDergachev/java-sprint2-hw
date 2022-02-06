package tasks;

public class Epic extends Task{



    public Epic(String name, String descriptionTask, String newStatusTask) {
        super(name, descriptionTask, newStatusTask);

    }
    public void setStatus(String status){
        this.status = status;
    }
    @Override
    public String toString() {
        return "tasks.Epic{" +
                "id:" + idTask + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}





