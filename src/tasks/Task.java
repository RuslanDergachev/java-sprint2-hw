package tasks;
import managerTasks.StatusTask;
import managerTasks.TypeTask;

public class Task {
    int idTask = 0;
    protected String name;
    protected String description;
    protected StatusTask status;

    public Task(String name, String descriptionTask, StatusTask status) {

        this.name = name;
        this.status = status;
        this.description = descriptionTask;
    }

    public void setId(int id) {
        idTask = id;
    }

    public int getIdTask(){
        return idTask;
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

    public TypeTask getType(){
        return TypeTask.TASK;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toCSV(){
    return getIdTask() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription();
    }

}
