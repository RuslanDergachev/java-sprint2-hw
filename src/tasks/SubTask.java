package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    protected int keyEpic;

    public SubTask(String name, String descriptionTask, StatusTask newStatusTask, int keyEpic, Duration duration, LocalDateTime startTime) {
        super(name, descriptionTask, newStatusTask, duration, startTime);
        this.keyEpic = keyEpic;

    }

    public SubTask(int taskId, String name, String description, StatusTask status, Duration duration, LocalDateTime startTime, int keyEpic) {
        super(taskId, name, description, status, duration, startTime);
        this.keyEpic = keyEpic;
    }

    @Override
    public String toString() {
        return "tasks.SubTask{" +
                "id:" + taskId + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", duration=" + duration + '\'' +
                "startTime=" + startTime +'\'' +
                ", keyEpic='" + keyEpic + '\'' +
                '}';
    }

    public int getKey() {
        return keyEpic;
    }

    @Override
    public TypeTask getType() {
        return TypeTask.SUBTASK;
    }

    @Override
    public String toCSV() {
        return super.toCSV() + "," + getKey();
    }

    @Override
    public LocalDateTime getStartTime(){
        return startTime;
    }

    @Override
    public LocalDateTime getEndTime(){
        return getStartTime().plus(getDuration());
    }
}