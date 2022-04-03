package historyTasks;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private HistoryList<Task> historyList = new HistoryList<>();

    @Override
    public void add(Task task) {
        historyList.addLast(task);
    }

    @Override
    public void remove(int id) {
        historyList.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyList.getTasks();
    }

    @Override
    public void printAllHistory(List<Task> history){
       historyList.printHistory(getHistory());
    }
}
