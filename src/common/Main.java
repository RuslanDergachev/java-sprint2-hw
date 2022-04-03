package common;

import managerTasks.TaskManager;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        Task taskTask = new Task("Пойти гулять", "Сегодня хорошая погода", StatusTask.NEW);
        taskManager.newTask(taskTask);

        Task taskTask2 = new Task("Побыть дома", "Сегодня дождь", StatusTask.NEW);
        taskManager.newTask(taskTask2);

        Epic taskEpic7 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(taskEpic7);

        Epic taskEpic5 = new Epic("Одеть шапку", "шапка должна быть весенней",
                StatusTask.NEW);
        taskManager.newEpic(taskEpic5);

        SubTask taskSubTask = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 3);
        taskManager.newSubTask(taskSubTask);

        SubTask taskSubTask3 = new SubTask("Взять перчатки", "Одеть шарфик",
                StatusTask.IN_PROGRESS, 3);
        taskManager.newSubTask(taskSubTask3);


        System.out.println(taskManager.getTask(1));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getTask(2));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getTask(2));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getTask(1));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getTask(1));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getEpic(3));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getEpic(4));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getSubTask(6));
        System.out.println("Проверка истории: " +taskManager.getHistory());
        System.out.println(taskManager.getSubTask(5));
        System.out.println("Проверка истории: " +taskManager.getHistory() +'\n');

//        taskManager.remove(3);
//        taskManager.remove(2);
//        taskManager.remove(5);
//        taskManager.remove(6);

        System.out.println("История просмотра задач:");
        taskManager.printAllHistory(taskManager.getHistory());
    }
}
