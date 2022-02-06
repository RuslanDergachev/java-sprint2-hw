import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int keyTask = 0;


    HashMap<Integer, Task> newTask = new HashMap<>();
    HashMap<Integer, Epic> newEpic = new HashMap<>();
    HashMap<Integer, SubTask> subTask = new HashMap<>();

    public int getId() {
        ++keyTask;
        return keyTask;
    }

    public void newTask(Task object) {
        int idTask = getId();
        newTask.put(idTask, object);
        object.setId(idTask);
    }

    public void newEpic(Epic object) {
        int idEpic = getId();
        newEpic.put(idEpic, object);
        object.setId(idEpic);
    }

    public void newSubTask(SubTask object) {
        int idSubTask = getId();
        subTask.put(idSubTask, object);
        statusCheckEpic(object.getKey());
        object.setId(idSubTask);
    }

    public ArrayList<Task> listAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Integer o : newTask.keySet()) {
            allTasks.add(newTask.get(o));
        }

        for (Integer o : newEpic.keySet()) {
            allTasks.add(newEpic.get(o));
        }

        for (Integer o : subTask.keySet()) {
            allTasks.add(subTask.get(o));
        }
        return allTasks;
    }

    public void deleteAllTasks() {
        newEpic.clear();
        newTask.clear();
        subTask.clear();
        System.out.println("Все задачи удалены.");
    }

    public Task getTask(int keyTask) {
        Task resultFound = null;
        for (Integer o : newTask.keySet()) {
            if (keyTask == o) {
                resultFound = newTask.get(o);
            }
        }
        return resultFound;
    }

    public Task getEpic(int keyEpic) {
        Task resultFound = null;
        for (Integer o : newEpic.keySet()) {
            if (keyEpic == o) {
                resultFound = newEpic.get(o);
            }
        }
        return resultFound;
    }

    public Task getSubTask(int keySubTask) {
        Task resultFound = null;
        for (Integer o : subTask.keySet()) {
            if (keySubTask == o) {
                resultFound = subTask.get(o);
            }
        }
        return resultFound;
    }

    public void deleteEpic(int keyEpic) {
        newEpic.remove(keyEpic);
        System.out.println("Задача № " + keyEpic + "удалена.");
    }

    public void deleteTask(int keyTask) {
        newTask.remove(keyTask);
        System.out.println("Задача № " + keyTask + "удалена.");
    }

    public void deleteSubTask(int keySubTask) {
        subTask.remove(keySubTask);
        statusCheckEpic(subTask.get(keySubTask).getKey());
        System.out.println("Задача № " + keySubTask + "удалена.");
    }

    public void updateEpic(Epic object, int keyEpic) {
        if (newEpic.containsKey(keyEpic)) {
            newEpic.put(keyEpic, object);
        }
    }

    public void updateTask(Epic object, int keyTask) {
        if (newTask.containsKey(keyTask)) {
            newTask.put(keyTask, object);

        }
    }

    public void updateSubTask(SubTask object, int keyEpic) {
        if (subTask.containsKey(keyEpic)) {
            subTask.put(keyEpic, object);
            statusCheckEpic(keyEpic);
        }
    }

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

    public void statusCheckEpic(int keyEpic) {
        boolean containsNewTasks = true;
        boolean containsDoneTasks = true;

        ArrayList<Task> listEpic = getEpicSubtasks(keyEpic);

        for (Task o : listEpic) {

            if (!o.getStatus().equals("NEW")) {
                containsNewTasks = false;
            }
            if (!o.getStatus().equals("DONE")) {
                containsDoneTasks = false;
            }

        }
        newEpic.get(keyEpic).setStatus("NEW");

        if (containsNewTasks || listEpic.isEmpty()) {
            newEpic.get(keyEpic).setStatus("NEW");
            return;
        }
        if (containsDoneTasks) {
            newEpic.get(keyEpic).setStatus("DONE");
            return;
        }
        newEpic.get(keyEpic).setStatus("IN PROGRESS");

    }

    public ArrayList<Task> getEpicSubtasks(int keyEpic) {
        ArrayList<Task> epicSubTask = new ArrayList<>();
        for (Integer j : subTask.keySet()) {
            if (keyEpic == subTask.get(j).getKey()) {
                epicSubTask.add(subTask.get(j));
            }
        }
        return epicSubTask;
    }
}
