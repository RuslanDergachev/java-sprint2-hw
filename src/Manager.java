import com.sun.source.tree.NewArrayTree;

import java.util.HashMap;

public class Manager {

    private int keyTask = 0;

    HashMap<Integer, Epic> newEpic = new HashMap<>();
    HashMap<Integer, Task> newTask = new HashMap<>();
    HashMap<Integer, SubTask> subTask = new HashMap<>();

    public void newEpic(Epic object) {
        newEpic.put(++keyTask, object);
    }

    public void newTask(Task object) {
        newTask.put(++keyTask, object);
        statusCheckEpic(object.getKeyEpic());
    }

    public void newSubTask(SubTask object) {
        subTask.put(++keyTask, object);
        if (newTask.get(object.getKeyTask()) != null) {
            statusCheckEpic(newTask.get(object.getKeyTask()).getKeyEpic());
        }
    }

    public void listAllTasks() {
        for (Integer o : newEpic.keySet()) {
            System.out.println("Идентификатор: " + o + " " + newEpic.get(o));
        }
        for (Integer o : newTask.keySet()) {
            System.out.println("Идентификатор: " + o + " " + newTask.get(o));
        }

        for (Integer o : subTask.keySet()) {
            System.out.println("Идентификатор: " + o + " " + subTask.get(o));
        }

    }

    public void deleteAllTasks() {
        newEpic.clear();
        newTask.clear();
        subTask.clear();
        System.out.println("Все задачи удалены.");
    }

    public void foundTask(int keyTask) {
        for (Integer o : newEpic.keySet()) {
            if (keyTask == o) {
                System.out.println("По заданному номеру " + keyTask + " найден эпик: " + newEpic.get(o));
            }
        }
        for (Integer o : newTask.keySet()) {
            if (keyTask == o) {
                System.out.println("По заданному номеру " + keyTask + " найдена задача: " + newEpic.get(o));
            }
        }

        for (Integer o : subTask.keySet()) {
            if (keyTask == o) {
                System.out.println("По заданному номеру " + keyTask + " найдена подзадача: " + subTask.get(o));
            }
        }
    }

    public void deleteEpic(int keyTask) {
        newEpic.remove(keyTask);
        System.out.println("Задача № " + keyTask + "удалена.");
    }

    public void deleteTask(int keyTask) {
        newTask.remove(keyTask);
        System.out.println("Задача № " + keyTask + "удалена.");
    }

    public void deleteSubTask(int keyTask) {
        subTask.remove(keyTask);
        System.out.println("Задача № " + keyTask + "удалена.");
    }

    public void updateEpic(Epic object, int keyTask) {
        if (newEpic.containsKey(keyTask)) {
            newEpic.put(keyTask, object);
        }
    }

    public void updateTask(Task object, int keyTask) {
        if (newTask.containsKey(keyTask)) {
            newTask.put(keyTask, object);
            statusCheckEpic(object.getKeyEpic());
        }
    }

    public void updateSubTask(SubTask object, int keyTask) {
        if (subTask.containsKey(keyTask)) {
            subTask.put(keyTask, object);
            statusCheckEpic(newTask.get(object.getKeyTask()).getKeyEpic());
        }
    }

    public void listTasks(int keyTask) {
        for (Integer j : newTask.keySet()) {
            newTask.get(j);
            if (keyTask == newTask.get(j).getKeyEpic()) {
                System.out.println(newTask.get(j));
                for (SubTask k : subTask.values()) {
                    if (j == k.getKeyTask()) {
                        System.out.println(k);
                    }
                }
            }
        }
    }

    public void statusCheckEpic(int keyTask) {
        boolean taskNew = true;
        boolean taskDone = true;
        boolean taskOut = false;

        for (Integer j : newTask.keySet()) {
            if (keyTask == newTask.get(j).getKeyEpic()) {
                taskOut = true;
                if (!newTask.get(j).getStatusTask().equals("NEW")) {
                    taskNew = false;
                }
                if (!newTask.get(j).getStatusTask().equals("DONE")) {
                    taskDone = false;
                }
                for (SubTask k : subTask.values()) {
                    if (j == k.getKeyTask()) {
                        if (!k.getStatusSubTask().equals("NEW")) {
                            taskNew = false;
                        }
                        if (!k.getStatusSubTask().equals("DONE")) {
                            taskDone = false;
                        }
                    }
                }
            }
        }

        if (taskNew || !taskOut) {
            newEpic.get(keyTask).setStatus("NEW");
            return;
        }
        if (taskDone) {
            newEpic.get(keyTask).setStatus("DONE");
            return;
        }
        newEpic.get(keyTask).setStatus("IN PROGRESS");
    }
}
