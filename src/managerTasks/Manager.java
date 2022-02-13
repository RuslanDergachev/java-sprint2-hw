package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import managerTasks.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public interface Manager {


    public int getId();

    public void newTask(Task object);

    public void newEpic(Epic object);

    public void newSubTask(SubTask object);

    public List<Task> listAllTasks();

    public void deleteAllTasks();

    public Task getTask(int keyTask);

    public Task getEpic(int keyEpic);

    public Task getSubTask(int keySubTask);

    public void deleteEpic(int keyEpic);

    public void deleteTask(int keyTask);

    public void deleteSubTask(int keySubTask);

    public void updateEpic(Epic object, int keyEpic);

    public void updateTask(Epic object, int keyTask);

    public void updateSubTask(SubTask object, int keyEpic);

    public void listTasks(int keyTask);

    public void statusCheckEpic(int keyEpic);

    public ArrayList<SubTask> getEpicSubtasks(int keyEpic);


}
