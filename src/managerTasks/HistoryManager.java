package managerTasks;
import java.util.*;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public interface HistoryManager {

    public void add(Task task);

    public List<Task> getHistory();

}

