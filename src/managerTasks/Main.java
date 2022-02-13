package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {

        Manager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        Task taskTask = new Task("Пойти гулять", "Сегодня хорошая погода", StatusTask.NEW);
        manager.newTask(taskTask);

        Task taskTask6 = new Task("Пойти гулять", "Сегодня хорошая погода", StatusTask.NEW);
        manager.newTask(taskTask6);

        Epic taskEpic7 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        manager.newEpic(taskEpic7);

        Epic taskEpic5 = new Epic("Одеть шапку", "шапка должна быть весенней",
                StatusTask.NEW);
        manager.newEpic(taskEpic5);

        Epic taskEpic8 = new Epic("Достать лыжи", "И санки тоже",
                StatusTask.IN_PROGRESS);
        manager.newEpic(taskEpic8);

        Task taskTask2 = new Task("Одеть кроссовки", "дистанция 3 км",
                StatusTask.IN_PROGRESS);
        manager.newTask(taskTask2);

        Task taskTask3 = new Task("Взять кота", "кот Мурзик",
                StatusTask.NEW);
        manager.newTask(taskTask3);

        SubTask taskSubTask = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 3);
        manager.newSubTask(taskSubTask);

        SubTask taskSubTask2 = new SubTask("Взять перчатки", "Одеть шарфик",
                StatusTask.IN_PROGRESS, 5);
        manager.newSubTask(taskSubTask2);


        //manager.deleteAllTasks();
        //manager.deleteTask(1);
        //manager.foundTask(3);
        //tasks.Epic taskEpic3 = new tasks.Epic("Остаться дома", "Надо отдохнуть", "IN PROGRESS");
        //manager.updateEpic(taskEpic3, 3);
        //manager.updateTask();
        //manager.updateSubTask();

        //manager.listTasks(1);
        System.out.println(manager.listAllTasks());
        System.out.println(manager.getSubTask(8));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(2));
        System.out.println(manager.getTask(1));

        System.out.println(historyManager.getHistory());
    }
}
