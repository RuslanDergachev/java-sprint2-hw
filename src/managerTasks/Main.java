package managerTasks;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task taskTask = new Task("Пойти гулять", "Сегодня хорошая погода", StatusTask.NEW);
        taskManager.newTask(taskTask);

        Task taskTask6 = new Task("Пойти гулять", "Сегодня хорошая погода", StatusTask.NEW);
        taskManager.newTask(taskTask6);

        Epic taskEpic7 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(taskEpic7);

        Epic taskEpic5 = new Epic("Одеть шапку", "шапка должна быть весенней",
                StatusTask.NEW);
        taskManager.newEpic(taskEpic5);

        Epic taskEpic8 = new Epic("Достать лыжи", "И санки тоже",
                StatusTask.IN_PROGRESS);
        taskManager.newEpic(taskEpic8);

        Task taskTask2 = new Task("Одеть кроссовки", "дистанция 3 км",
                StatusTask.IN_PROGRESS);
        taskManager.newTask(taskTask2);

        Task taskTask3 = new Task("Взять кота", "кот Мурзик",
                StatusTask.NEW);
        taskManager.newTask(taskTask3);

        SubTask taskSubTask = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 3);
        taskManager.newSubTask(taskSubTask);

        SubTask taskSubTask2 = new SubTask("Взять перчатки", "Одеть шарфик",
                StatusTask.IN_PROGRESS, 5);
        taskManager.newSubTask(taskSubTask2);

        System.out.println(taskManager.listAllTasks());
        System.out.println(taskManager.getSubTask(8));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(1));

        System.out.println(taskManager.getHistory());
    }
}
