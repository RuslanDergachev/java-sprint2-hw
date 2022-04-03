package historyTasks;
import java.util.*;
import tasks.Task;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();

    void printAllHistory(List<Task> history);



}

