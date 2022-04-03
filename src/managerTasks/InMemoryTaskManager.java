package managerTasks;

import common.Managers;
import historyTasks.HistoryManager;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    protected HistoryManager historyManager = Managers.getDefaultHistory();

    public void setKeyTask(int keyTask) {
        this.keyTask = keyTask;
    }

    protected int keyTask = 0;

    HashMap<Integer, Task> taskMap = new HashMap<>();
    HashMap<Integer, Epic> epicMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskMap = new HashMap<>();

    @Override
    public int getId() {
        return ++keyTask;
    }

    @Override
    public void newTask(Task object) {
        int idTask = getId();
        taskMap.put(idTask, object);
        object.setId(idTask);
    }

    @Override
    public void newEpic(Epic object) {
        int idEpic = getId();
        epicMap.put(idEpic, object);
        object.setId(idEpic);
    }

    @Override
    public void newSubTask(SubTask object) {
        int idSubTask = getId();
        subTaskMap.put(idSubTask, object);
        statusCheckEpic(object.getKey());
        object.setId(idSubTask);
    }

    @Override
    public List<Task> listAllTasks() {
        List<Task> allTasks = new ArrayList<>(taskMap.values());
        allTasks.addAll(epicMap.values());
        allTasks.addAll(subTaskMap.values());
        return allTasks;
    }

    @Override
    public void deleteAllTasks() {
        epicMap.clear();
        taskMap.clear();
        subTaskMap.clear();
        System.out.println("Все задачи удалены.");
    }

    @Override
    public Task getTask(int keyTask) {
        Task resultFound = null;
        for (Integer o : taskMap.keySet()) {
            if (keyTask == o) {
                resultFound = taskMap.get(o);
                historyManager.add(resultFound);
            }
        }
        return resultFound;
    }

    @Override
    public Task getEpic(int keyEpic) {
        Task resultFound = null;
        for (Integer o : epicMap.keySet()) {
            if (keyEpic == o) {
                resultFound = epicMap.get(o);
                historyManager.add(resultFound);
            }
        }
        return resultFound;
    }

    @Override
    public Task getSubTask(int keySubTask) {
        Task resultFound = null;
        for (Integer o : subTaskMap.keySet()) {
            if (keySubTask == o) {
                resultFound = subTaskMap.get(o);
                historyManager.add(resultFound);
            }
        }
        return resultFound;
    }

    @Override
    public void deleteEpic(int keyEpic) {
        epicMap.remove(keyEpic);
        System.out.println("Задача № " + keyEpic + "удалена.");
    }

    @Override
    public void deleteTask(int keyTask) {
        taskMap.remove(keyTask);
        System.out.println("Задача № " + keyTask + "удалена.");
    }

    @Override
    public void deleteSubTask(int keySubTask) {
        subTaskMap.remove(keySubTask);
        statusCheckEpic(subTaskMap.get(keySubTask).getKey());
        System.out.println("Задача № " + keySubTask + "удалена.");
    }

    @Override
    public void updateEpic(Epic object, int keyEpic) {
        if (epicMap.containsKey(keyEpic)) {
            epicMap.put(keyEpic, object);
        }
    }

    @Override
    public void updateTask(Epic object, int keyTask) {
        if (taskMap.containsKey(keyTask)) {
            taskMap.put(keyTask, object);

        }
    }

    @Override
    public void updateSubTask(SubTask object, int keyEpic) {
        if (subTaskMap.containsKey(keyEpic)) {
            subTaskMap.put(keyEpic, object);
            statusCheckEpic(keyEpic);
        }
    }

    @Override
    public void listTasks(int keyTask) {
        for (Integer j : epicMap.keySet()) {
            epicMap.get(j);
            if (keyTask == subTaskMap.get(j).getKey()) {
                System.out.println(epicMap.get(j));
                for (SubTask k : subTaskMap.values()) {
                    if (j == k.getKey()) {
                        System.out.println(k);
                    }
                }
            }
        }
    }

    @Override
    public void statusCheckEpic(int keyEpic) {
        boolean containsNewTasks = true;
        boolean containsDoneTasks = true;
        Epic objectKeyEpic = epicMap.get(keyEpic);

        ArrayList<SubTask> listEpic = getEpicSubtasks(keyEpic);

        for (Task o : listEpic) {

            if (!o.getStatus().equals(StatusTask.NEW)) {
                containsNewTasks = false;
            }
            if (!o.getStatus().equals(StatusTask.DONE)) {
                containsDoneTasks = false;
            }
        }

        if (containsNewTasks || listEpic.isEmpty()) {
            objectKeyEpic.setStatus(StatusTask.NEW);
            return;
        }
        if (containsDoneTasks) {
            objectKeyEpic.setStatus(StatusTask.DONE);
            return;
        }
        objectKeyEpic.setStatus(StatusTask.IN_PROGRESS);
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(int keyEpic) {
        ArrayList<SubTask> epicSubTask = new ArrayList<>();
        for (Integer j : subTaskMap.keySet()) {
            if (keyEpic == subTaskMap.get(j).getKey()) {
                epicSubTask.add(subTaskMap.get(j));
            }
        }
        return epicSubTask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void remove(int id){
        historyManager.remove(id);
    }
    @Override
    public void printAllHistory(List<Task> history){
        historyManager.printAllHistory(getHistory());
    }
    @Override
    public void printAllTasks(List<Task> tasks){
        for (Task task: tasks){
            System.out.println(task);
        }
    }
}
