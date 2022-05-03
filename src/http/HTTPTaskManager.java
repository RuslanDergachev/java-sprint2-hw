package http;

import managerTasks.FileBackedTasksManager;
import managerTasks.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager implements TaskManager {

    URI url;
    KVTaskClient kvTaskClient;

    public HTTPTaskManager(URI url) {
        super(null);
        this.url = url;
        this.kvTaskClient = new KVTaskClient(url);
    }

    @Override
    protected void save() {

        List<Task> allTasks = listAllTasks();
        StringBuilder backedTasks = new StringBuilder();
        String tasks;

        try {
            String header = ("id,type,status,description,duration,startTime,epic\n");
            backedTasks.append(header);
            for (Task o : allTasks) {
                backedTasks.append(o.toCSV() + '\n');
            }
            backedTasks.append("\n" + historyTask(historyManager.getHistory()));
            tasks = backedTasks.toString();
            kvTaskClient.put("1234567", tasks);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void load() throws IOException, InterruptedException {
        boolean counter = false;
        boolean emptyLine = false;
        String newTasks = kvTaskClient.load("1234567");
        String[] reloadTasks = newTasks.split("/n");
        for(String task: reloadTasks){
            if (!counter) {
                counter = true;
                continue;
            }
            if (task.isEmpty()) {
                emptyLine = true;
            } else if (emptyLine) {
                reloadHistory(getHistoryTasksList(task));
            } else {
                addTaskFromFile(taskFromString(task));
            }
        }
    }
}
