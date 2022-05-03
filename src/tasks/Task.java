package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    int taskId;
    protected String name;
    protected String description;
    protected StatusTask status;
    protected Duration duration;
    protected LocalDateTime startTime;


    public Task(String name, String descriptionTask, StatusTask status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.status = status;
        this.description = descriptionTask;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(int taskId, String name, String description, StatusTask status, Duration duration, LocalDateTime startTime) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public void setId(int id) {
        taskId = id;
    }

    public int getId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id:" + taskId + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", duration=" + duration + '\'' +
                "startTime=" + startTime +'\'' +
                '}';
    }

    public StatusTask getStatus() {
        return status;
    }

    public TypeTask getType() {
        return TypeTask.TASK;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration(){
        return duration;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public LocalDateTime getEndTime(){
        return getStartTime().plus(getDuration());
    }

    public String toCSV() {
        return getId() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription() + ","
                + getDuration() + "," + getStartTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && name.equals(task.name) && description.equals(task.description) && status == task.status && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, name, description, status, duration, startTime);
    }
}
