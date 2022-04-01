package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    String taskFile;

    public FileBackedTasksManager(String newFile) {
        this.taskFile = newFile;
    }

    @Override
    public void newTask(Task object) {
        super.newTask(object);
        save();
    }

    @Override
    public void newEpic(Epic object) {
        super.newEpic(object);
        save();
    }

    @Override
    public void newSubTask(SubTask object) {
        super.newSubTask(object);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTask(int keyTask) {
        Task newTask = super.getTask(keyTask);
        save();
        return newTask;
    }

    @Override
    public Task getEpic(int keyEpic) {
        Task newEpic = super.getEpic(keyEpic);
        save();
        return newEpic;
    }

    @Override
    public Task getSubTask(int keySubTask) {
        Task newSubTask = super.getSubTask(keySubTask);
        save();
        return newSubTask;
    }

    @Override
    public void deleteEpic(int keyEpic) {
        super.deleteEpic(keyEpic);
        save();
    }

    @Override
    public void deleteTask(int keyTask) {
        super.deleteTask(keyTask);
        save();
    }

    @Override
    public void deleteSubTask(int keySubTask) {
        super.deleteSubTask(keySubTask);
        save();
    }

    @Override
    public void updateEpic(Epic object, int keyEpic) {
        super.updateEpic(object, keyEpic);
        save();
    }

    @Override
    public void updateTask(Epic object, int keyTask) {
        super.updateTask(object, keyTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask object, int keyEpic) {
        super.updateSubTask(object, keyEpic);
        save();
    }

    static Task taskFromString(String task) {
        String[] line = task.split(",");
        Task outTask = null;

        if ("TASK".equals(line[1])) {
            outTask = new Task(Integer.parseInt(line[0]), line[2], line[4], StatusTask.valueOf(line[3]));
        }
        if ("EPIC".equals(line[1])) {
            outTask = new Epic(Integer.parseInt(line[0]), line[2], line[4], StatusTask.valueOf(line[3]));
        }
        if ("SUBTASK".equals(line[1])) {
            outTask = new SubTask(Integer.parseInt(line[0]), line[2], line[4], StatusTask.valueOf(line[3]), Integer.parseInt(line[5]));
        }
        return outTask;
    }

    private void save() {
        try (Writer tasksFile = new FileWriter(taskFile)) {
            tasksFile.write("id,type,status,description,epic\n");
            List<Task> allTasks = listAllTasks();

            for (Task o : allTasks) {
                tasksFile.write(o.toCSV() + '\n');
            }
            tasksFile.write("\n" + historyTask(historyManager.getHistory()));
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    static String historyTask(List<Task> listHistory) {
        StringBuilder allHistory = new StringBuilder();
        int saveId;
        String idHistoryTask;
        for (Task history : listHistory) {
            saveId = history.getTaskId();
            idHistoryTask = Integer.toString(saveId);
            allHistory.append(idHistoryTask).append(",");
        }
        return allHistory.toString();
    }

    static List<Integer> getHistoryTasksList(String saveHistoryTasks) {
        List<Integer> reloadTaskHistory = new ArrayList<>();
        String[] reloadHistory = saveHistoryTasks.split(",");
        for (String history : reloadHistory) {
            int idHistory = Integer.parseInt(history);
            reloadTaskHistory.add(idHistory);
        }
        return reloadTaskHistory;
    }

    static FileBackedTasksManager readFileOutBacked(File file) throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file.getPath());
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String line;
        boolean counter = false;
        boolean emptyLine = false;

        while (br.ready()) {
            line = (br.readLine());// hist
            if (!counter) {
                counter = true;
                continue;
            }
            if (line.isEmpty()) {
                emptyLine = true;
            } else {
                if (emptyLine) {
                    fileBackedTasksManager.reloadHistory(getHistoryTasksList(line));
                } else {
                    fileBackedTasksManager.addTaskFromFile(taskFromString(line));
                }
            }
        }
        br.close();
        return fileBackedTasksManager;
    }

    private void addTaskFromFile(Task task) {
        int idTask = task.getTaskId();

        if (task.getType() == TypeTask.TASK) {
            taskMap.put(idTask, task);
            setKeyTasks(idTask);
        }
        if (task.getType() == TypeTask.EPIC) {
            epicMap.put(idTask, (Epic) task);
            setKeyTasks(idTask);
        }
        if (task.getType() == TypeTask.SUBTASK) {
            subTaskMap.put(idTask, (SubTask) task);
            setKeyTasks(idTask);
        }
    }

    private void setKeyTasks(int idTask) {
        if (idTask > keyTask) {
            keyTask = idTask;
        }
    }

    private void reloadHistory(List<Integer> idHistory) {
        for (Integer i : idHistory) {
            if (taskMap.containsKey(i)) {
                historyManager.add(taskMap.get(i));
            }
            if (epicMap.containsKey(i)) {
                historyManager.add(epicMap.get(i));
            }
            if (subTaskMap.containsKey(i)) {
                historyManager.add(subTaskMap.get(i));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File taskBacked = new File("taskBacked.csv");
        FileBackedTasksManager manager = readFileOutBacked(taskBacked);

        Task taskTask = new Task("Пойти гулять", "Сегодня хорошая погода", StatusTask.NEW);
        manager.newTask(taskTask);

        Task taskTask2 = new Task("Побыть дома", "Сегодня дождь", StatusTask.NEW);
        manager.newTask(taskTask2);

        Epic taskEpic7 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        manager.newEpic(taskEpic7);

        Epic taskEpic5 = new Epic("Одеть шапку", "шапка должна быть весенней",
                StatusTask.NEW);
        manager.newEpic(taskEpic5);

        SubTask taskSubTask = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 3);
        manager.newSubTask(taskSubTask);

        SubTask taskSubTask3 = new SubTask("Взять перчатки", "Одеть шарфик",
                StatusTask.IN_PROGRESS, 3);
        manager.newSubTask(taskSubTask3);

        manager.getTask(2);
        manager.getSubTask(5);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getEpic(4);
        manager.getSubTask(6);//

        FileBackedTasksManager manager2 = readFileOutBacked(taskBacked);
        System.out.println("Проверка истории менеджера 1: " + manager.getHistory());
        System.out.println(manager.listAllTasks());
        System.out.println("Проверка истории менеджера 2: " + manager2.getHistory());
        System.out.println(manager2.listAllTasks());
    }
}
