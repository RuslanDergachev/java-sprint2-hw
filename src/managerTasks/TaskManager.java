package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

interface TaskManager {


    int getId();

    void newTask(Task object);

    void newEpic(Epic object);

    void newSubTask(SubTask object);

    List<Task> listAllTasks();

    void deleteAllTasks();

    Task getTask(int keyTask);

    Task getEpic(int keyEpic);

    Task getSubTask(int keySubTask);

    void deleteEpic(int keyEpic);

    void deleteTask(int keyTask);

    void deleteSubTask(int keySubTask);

    void updateEpic(Epic object, int keyEpic);

    void updateTask(Epic object, int keyTask);

    void updateSubTask(SubTask object, int keyEpic);

    void listTasks(int keyTask);

    void statusCheckEpic(int keyEpic);

    ArrayList<SubTask> getEpicSubtasks(int keyEpic);

    List<Task> getHistory();
}
