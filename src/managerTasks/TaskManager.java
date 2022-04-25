package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    int getId();

    void newTask(Task object);

    void newEpic(Epic object);

    void newSubTask(SubTask object);

    List<Task> listAllTasks();

    void deleteAllTasks();

    Task getTask(int keyTask);

    Epic getEpic(int keyEpic);

    SubTask getSubTask(int keySubTask);

    void deleteEpic(int keyEpic);

    void deleteTask(int keyTask);

    void deleteSubTask(int keySubTask);

    void updateEpic(Epic object, int keyEpic);

    void updateTask(Task object, int keyTask);

    void updateSubTask(SubTask object, int keyEpic);

    void statusCheckEpic(int keyEpic);

    ArrayList<SubTask> getEpicSubtasks(int keyEpic);

    void listTasks(int keyTask);

    List<Task> getHistory();

    void remove(int id);

    void printAllHistory(List<Task> history);

    void printAllTasks(List<Task> tasks);

    public List<Task> getPrioritizedTasks(List<Task> tasks);

    public List<Task> listTasksAndSubTasks();

    Boolean isTaskCrossedInTime(Task task);

}
