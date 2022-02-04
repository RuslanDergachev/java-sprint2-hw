import java.util.Scanner;

public class Main {
        public static void main(String[] args) {

        Manager manager = new Manager();

        Epic taskEpic = new Epic("Пойти гулять", "Сегодня хорошая погода", "NEW");
        manager.newEpic(taskEpic);

        Epic taskEpic2 = new Epic("Пойти побегать", "Стадион рядом", "NEW");
        manager.newEpic(taskEpic2);

        Task taskTask = new Task("Одеть шапку", "шапка должна быть весенней",
                "NEW", 1);
        manager.newTask(taskTask);

        Task taskTask2 = new Task("Одеть кроссовки", "дистанция 3 км",
                "IN PROGRESS", 2);
        manager.newTask(taskTask2);

        Task taskTask3 = new Task("Взять кота", "кот Мурзик",
                "NEW", 1);
        manager.newTask(taskTask3);

        SubTask taskSubTask = new SubTask("Взять сумку", "Сумку взять маленькую",
                "NEW", 2);
        manager.newSubTask(taskSubTask);

        SubTask taskSubTask2 = new SubTask("Взять перчатки", "Одеть шарфик",
                        "DONE", 4);
        manager.newSubTask(taskSubTask2);


        //manager.deleteAllTasks();
        //manager.deleteTask(1);
        //manager.foundTask(3);
        //Epic taskEpic3 = new Epic("Остаться дома", "Надо отдохнуть", "IN PROGRESS");
        //manager.updateEpic(taskEpic3, 2);
        //manager.updateTask();
        //manager.updateSubTask();

        manager.listTasks(1);
        manager.listAllTasks();

    }
}
