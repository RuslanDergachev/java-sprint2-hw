package managerTasks;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private LinkedList<Task> historyViewTasks = new LinkedList<>();

    @Override
    public void add(Task task){
        setHistoryViewSize();
        historyViewTasks.addLast(task);
    }

    private void setHistoryViewSize() {
        if (historyViewTasks.size() > 9) {
            historyViewTasks.removeFirst();
        }
    }
    @Override
    public List<Task> getHistory() {
        return historyViewTasks;
    }
}
