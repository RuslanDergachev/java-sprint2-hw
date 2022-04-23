package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Epic extends Task {

    LocalDateTime endTime;

    public Epic(String name, String descriptionTask, StatusTask newStatusTask) {
        super(name, descriptionTask, newStatusTask, null, null);
    }

    public Epic(int taskId, String name, String description, StatusTask status) {
        super(taskId, name, description, status, null, null);
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "id:" + taskId + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration + '\'' +
                "startTime=" + startTime +'\'' +
                "endTime=" + endTime +'\'' +
                '}';
    }

    @Override
    public TypeTask getType() {
        return TypeTask.EPIC;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toCSV() {
        return getId() + "," + getType() + "," + getName() + "," + getStatus() + "," + getDescription();
    }
}