package managerTasks;

import common.Managers;
import historyTasks.HistoryManager;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected HistoryManager historyManager = Managers.getDefaultHistory();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
    protected List<Task> prioritizedTasks;

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
        if(timeCrossingTasks(object)) {
            int idTask = getId();
            taskMap.put(idTask, object);
            object.setId(idTask);
        }
    }

    @Override
    public void newEpic(Epic object) {
        int idEpic = getId();
        epicMap.put(idEpic, object);
        object.setId(idEpic);
    }

    @Override
    public void newSubTask(SubTask object) {
        if(timeCrossingTasks(object)) {
            int idSubTask = getId();
            subTaskMap.put(idSubTask, object);
            statusCheckEpic(object.getKey());
            object.setId(idSubTask);
        }
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
    public Epic getEpic(int keyEpic) {
        Epic resultFound = null;
        for (Integer o : epicMap.keySet()) {
            if (keyEpic == o) {
                resultFound = epicMap.get(o);
                historyManager.add(resultFound);
            }
        }
        return resultFound;
    }

    @Override
    public SubTask getSubTask(int keySubTask) {
        SubTask resultFound = null;
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
        int keyEpic = subTaskMap.get(keySubTask).getKey();
        subTaskMap.remove(keySubTask);
        statusCheckEpic(keyEpic);
        System.out.println("Задача № " + keySubTask + "удалена.");
    }

    @Override
    public void updateEpic(Epic object, int keyEpic) {
        if (epicMap.containsKey(keyEpic)) {
            epicMap.put(keyEpic, object);
        }
    }

    @Override
    public void updateTask(Task object, int keyTask) {
        if(timeCrossingTasks(object)) {
            if (taskMap.containsKey(keyTask)) {
                taskMap.put(keyTask, object);
                getPrioritizedTasks(listTasksAndSubTasks());
            }
        }
    }

    @Override
    public void updateSubTask(SubTask object, int idSubTask) {
        if(timeCrossingTasks(object)) {
            if (subTaskMap.containsKey(idSubTask)) {
                subTaskMap.put(idSubTask, object);
                statusCheckEpic(object.getKey());
            }
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
        objectKeyEpic.setDuration(returnDurationSubtaskForEpic(listEpic));
        objectKeyEpic.setStartTime(returnStartTime(listEpic));
        objectKeyEpic.setEndTime(getEndTime(returnDurationSubtaskForEpic(listEpic), returnStartTime(listEpic)));

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
    public void remove(int id) {
        historyManager.remove(id);
    }

    @Override
    public void printAllHistory(List<Task> history) {
        historyManager.printAllHistory(getHistory());
    }

    @Override
    public void printAllTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public LocalDateTime getEndTime(Duration duration, LocalDateTime startTime) {
        if (startTime == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public Duration returnDurationSubtaskForEpic(ArrayList<SubTask> listEpic) {
        Duration allDuration = null;
        for (SubTask subTask : listEpic) {
            if (allDuration == null) {
                allDuration = subTask.getDuration();
                continue;
            }
            allDuration = allDuration.plus(subTask.getDuration());
        }
        return allDuration;
    }

    public LocalDateTime returnStartTime(ArrayList<SubTask> listEpic) {
        LocalDateTime allSubTaskStartTime = null;
        for (SubTask subTask : listEpic) {
            if (allSubTaskStartTime == null) {
                allSubTaskStartTime = subTask.getStartTime();
            }
            if (allSubTaskStartTime != null && allSubTaskStartTime.isAfter(subTask.getStartTime())) {
                allSubTaskStartTime = subTask.getStartTime();
            }
        }
        return allSubTaskStartTime;
    }

    Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getId() == o2.getId()) return 0;
            if (o1.getStartTime() == null && o2.getStartTime() == null) {
                if (o1.getId() > o2.getId()) return -1;
            } else if (o1.getStartTime() != null && o2.getStartTime() != null) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
            return 1;
        }
    };

    public List<Task> getPrioritizedTasks(List<Task> tasks) {
        Set<Task> prioritizedTasks = new TreeSet<>(comparator.reversed());
        prioritizedTasks.addAll(tasks);
        return new ArrayList<>(prioritizedTasks);
    }

    public List<Task> listTasksAndSubTasks() {
        List<Task> tasksAndSubTasks = new ArrayList<>(taskMap.values());
        tasksAndSubTasks.addAll(subTaskMap.values());
        return tasksAndSubTasks;
    }

    public Boolean timeCrossingTasks(Task task) {
        prioritizedTasks  = getPrioritizedTasks(listTasksAndSubTasks());
        boolean timeCross = true;
        if (task != null && task.getStartTime()!=null) {
            for (Task newTask : prioritizedTasks) {
                if (task.getStartTime().isBefore(newTask.getEndTime()) &&
                        task.getEndTime().isAfter(newTask.getStartTime())) {
                    timeCross = false;
                    System.out.println("Время выполнения задачи с " + newTask.getStartTime() + " до " +
                            newTask.getEndTime() + " занято. Выберите другое время выполнения задачи");
                }
            }
        }
        return timeCross;
    }
}
