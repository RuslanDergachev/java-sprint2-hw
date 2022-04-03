package common;

import historyTasks.HistoryManager;
import historyTasks.InMemoryHistoryManager;
import managerTasks.InMemoryTaskManager;
import managerTasks.TaskManager;

public class Managers  {

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

}
