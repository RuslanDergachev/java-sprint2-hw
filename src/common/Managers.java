package common;

import historyTasks.HistoryManager;
import historyTasks.InMemoryHistoryManager;
import http.HTTPTaskManager;
import managerTasks.InMemoryTaskManager;
import managerTasks.TaskManager;

import java.net.URI;

public class Managers  {
    static URI url = URI.create("http://localhost:8078/");

    public static TaskManager getDefault(){
        return new HTTPTaskManager(url);
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
