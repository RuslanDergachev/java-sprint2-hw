package tasks;

import managerTasks.StatusTask;
import managerTasks.TypeTask;

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

    @Override
    public TypeTask getType(){
        return TypeTask.EPIC;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toCSV(){
        return getIdTask() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription();
    }
}





