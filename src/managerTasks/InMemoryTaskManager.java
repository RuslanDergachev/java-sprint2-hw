package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private HistoryManager historyManager = Managers.getDefaultHistory();

    private int keyTask = 0;

    HashMap<Integer, Task> newTask = new HashMap<>();
    HashMap<Integer, Epic> newEpic = new HashMap<>();
    HashMap<Integer, SubTask> subTask = new HashMap<>();

    @Override
    public int getId() {
        return ++keyTask;
    }

    @Override
    public void newTask(Task object) {
        int idTask = getId();
        newTask.put(idTask, object);
        object.setId(idTask);
    }

    @Override
    public void newEpic(Epic object) {
        int idEpic = getId();
        newEpic.put(idEpic, object);
        object.setId(idEpic);
    }

    @Override
    public void newSubTask(SubTask object) {
        int idSubTask = getId();
        subTask.put(idSubTask, object);
        statusCheckEpic(object.getKey());
        object.setId(idSubTask);
    }

    @Override
    public List<Task> listAllTasks() {
        List<Task> allTasks = new ArrayList<>(newTask.values());
        allTasks.addAll(newEpic.values());
        allTasks.addAll(subTask.values());

        return allTasks;
    }

    @Override
    public void deleteAllTasks() {
        newEpic.clear();
        newTask.clear();
        subTask.clear();
        System.out.println("Все задачи удалены.");
    }

    @Override
    public Task getTask(int keyTask) {
        Task resultFound = null;
        for (Integer o : newTask.keySet()) {
            if (keyTask == o) {
                resultFound = newTask.get(o);
                historyManager.add(resultFound);
            }
        }
        return resultFound;
    }

    @Override
    public Task getEpic(int keyEpic) {
        Task resultFound = null;
        for (Integer o : newEpic.keySet()) {
            if (keyEpic == o) {
                resultFound = newEpic.get(o);
                historyManager.add(resultFound);
            }
        }
        return resultFound;
    }

    @Override
    public Task getSubTask(int keySubTask) {
        Task resultFound = null;
        for (Integer o : subTask.keySet()) {
            if (keySubTask == o) {
                resultFound = subTask.get(o);
                historyManager.add(resultFound);
            }
        }
        return resultFound;
    }

    @Override
    public void deleteEpic(int keyEpic) {
        newEpic.remove(keyEpic);
        System.out.println("Задача № " + keyEpic + "удалена.");
    }

    @Override
    public void deleteTask(int keyTask) {
        newTask.remove(keyTask);
        System.out.println("Задача № " + keyTask + "удалена.");
    }

    @Override
    public void deleteSubTask(int keySubTask) {
        subTask.remove(keySubTask);
        statusCheckEpic(subTask.get(keySubTask).getKey());
        System.out.println("Задача № " + keySubTask + "удалена.");
    }

    @Override
    public void updateEpic(Epic object, int keyEpic) {
        if (newEpic.containsKey(keyEpic)) {
            newEpic.put(keyEpic, object);
        }
    }

    @Override
    public void updateTask(Epic object, int keyTask) {
        if (newTask.containsKey(keyTask)) {
            newTask.put(keyTask, object);

        }
    }

    @Override
    public void updateSubTask(SubTask object, int keyEpic) {
        if (subTask.containsKey(keyEpic)) {
            subTask.put(keyEpic, object);
            statusCheckEpic(keyEpic);
        }
    }

    @Override
    public void listTasks(int keyTask) {
        for (Integer j : newEpic.keySet()) {
            newEpic.get(j);
            if (keyTask == subTask.get(j).getKey()) {
                System.out.println(newEpic.get(j));
                for (SubTask k : subTask.values()) {
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
        Epic objectKeyEpic = newEpic.get(keyEpic);

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
        for (Integer j : subTask.keySet()) {
            if (keyEpic == subTask.get(j).getKey()) {
                epicSubTask.add(subTask.get(j));
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
}
