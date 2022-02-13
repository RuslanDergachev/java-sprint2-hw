package tasks;

import managerTasks.StatusTask;

public class Epic extends Task{



    public Epic(String name, String descriptionTask, StatusTask newStatusTask) {
        super(name, descriptionTask, newStatusTask);

    }
    public void setStatus(StatusTask status){
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





