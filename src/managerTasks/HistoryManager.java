package managerTasks;
import java.util.*;
import tasks.Task;

interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();



}

