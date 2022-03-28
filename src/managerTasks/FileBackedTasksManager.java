package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        if (line[1].equals("TASK")) {
            Task newTask = new Task(line[2], line[4], StatusTask.valueOf(line[3]));
            newTask.setId(Integer.parseInt(line[0]));

            outTask = newTask;
        }
        if (line[1].equals("EPIC")) {
            Task newEpic = new Epic(line[2], line[4], StatusTask.valueOf(line[3]));
            newEpic.setId(Integer.parseInt(line[0]));
            outTask = newEpic;
        }
        if (line[1].equals("SUBTASK")) {
            Task newSubtask = new SubTask(line[2], line[4], StatusTask.valueOf(line[3]), Integer.parseInt(line[5]));
            newSubtask.setId(Integer.parseInt(line[0]));
            outTask = newSubtask;
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
            tasksFile.write("\n" + historyTask(historyManager));

        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    static String historyTask(HistoryManager manager) {
        List<Task> getHistory = manager.getHistory();
        StringBuilder allHistory = new StringBuilder();
        int saveId;
        String i;
        for (Task o : getHistory) {
            saveId = o.getIdTask();
            i = Integer.toString(saveId);
            allHistory.append(i).append(",");
        }
        return allHistory.toString();
    }

    static List<Integer> fromHistoryTasks(String saveHistoryTasks) {
        List<Integer> reloadTaskHistory = new ArrayList<>();
        String[] reloadHistory = saveHistoryTasks.split(",");
        for (String i : reloadHistory) {
            int o = Integer.parseInt(i);
            reloadTaskHistory.add(o);
        }
        return reloadTaskHistory;
    }

    static FileBackedTasksManager readFileOutBacked(File file) throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file.getPath());//получить путь

        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String line;
        boolean counter = false;
        boolean emptyLine = false;

        while (br.ready()) {
            line = (br.readLine());
            if (!counter) {
                counter = true;
                continue;
            }
            if (counter) {
                if (!line.equals("")) {
                    fileBackedTasksManager.addTaskFromFile(taskFromString(line));
                }
            }
            if (line.isEmpty()) {
                emptyLine = true;
                counter = false;
            }
            if (emptyLine) {
                if (!line.equals("")) {
                    fileBackedTasksManager.reloadHistory(fromHistoryTasks(line));
                }
            }
        }
        br.close();
        return fileBackedTasksManager;
    }


    private void addTaskFromFile(Task task) {

        if (task.getType() == TypeTask.TASK) {
            newTask.put(task.getIdTask(), task);
            if (task.getIdTask() > keyTask) {
                keyTask = task.getIdTask();
            }
        }
        if (task.getType() == TypeTask.EPIC) {
            newEpic.put(task.getIdTask(), (Epic) task);
            if (task.getIdTask() > keyTask) {
                keyTask = task.getIdTask();
            }
        }
        if (task.getType() == TypeTask.SUBTASK) {
            subTask.put(task.getIdTask(), (SubTask) task);
            if (task.getIdTask() > keyTask) {
                keyTask = task.getIdTask();
            }
        }
    }

    private void reloadHistory(List<Integer> idHistory) {
        for (Integer i : idHistory) {
            if (newTask.containsKey(i)) {
                historyManager.add(newTask.get(i));
            }
            if (newEpic.containsKey(i)) {
                historyManager.add(newEpic.get(i));
            }
            if (subTask.containsKey(i)) {
                historyManager.add(subTask.get(i));
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


        manager.getTask(1);
        manager.getTask(2);
        manager.getEpic(3);
        manager.getEpic(4);
        manager.getEpic(5);
        manager.getEpic(7);

        System.out.println("Проверка истории: " + manager.getHistory());
        System.out.println(manager.getTask(1));
        System.out.println("Проверка истории: " + manager.getHistory());
        System.out.println(manager.getTask(2));

        System.out.println(manager.listAllTasks());

    }
}
