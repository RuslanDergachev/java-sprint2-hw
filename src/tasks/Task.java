package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public String getName() {
        return name;
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
}
