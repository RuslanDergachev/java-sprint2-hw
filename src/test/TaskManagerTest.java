package test;

import managerTasks.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;

    protected int getEpicId(Epic epic) {
        return epic.getId();

    }

    abstract T createManager();

    @Test// проверка создания Task
    public void createTask() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 0, 0));
        taskManager.newTask(task1);
        final Task savedTask = taskManager.getTask(task1.getId());
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(task1, savedTask, "Задачи не совпадают");
        final List<Task> tasks = taskManager.listAllTasks();
        assertEquals(1, tasks.size(), "Задача не создана");
    }

    @Test// проверка создания Epic
    public void createEpic() {
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(epic1);
        final int epicId = epic1.getId();
        final Epic savedEpic = taskManager.getEpic(epicId);
        assertEquals(epic1, savedEpic, "Задачи не совпадают");
        assertNotNull(savedEpic, "Задача не найдена");
        final List<Task> tasks = taskManager.listAllTasks();
        assertNotNull(tasks, "Задачи не возвращаются");
        final ArrayList<SubTask> listSubTask = taskManager.getEpicSubtasks(epicId);
        assertEquals(0, listSubTask.size(), "У нового эпика не может быть подзадач");
        assertEquals(StatusTask.NEW, savedEpic.getStatus(), "У нового эпика статус должен быть NEW");
    }

    @Test// проверка создания SubTask и наличие эпика
    public void createSubTask() {
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 1, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newEpic(epic1);
        taskManager.newSubTask(subtask1);
        final int subTaskId = subtask1.getId();
        final int epicId = epic1.getId();
        final SubTask savedSubTask = taskManager.getSubTask(subTaskId);
        final int keyEpic = savedSubTask.getKey();
        assertEquals(keyEpic, epicId, "EPIC не найден");
        assertEquals(subtask1, savedSubTask, "Подзадачи не совпадают");
        final List<Task> tasks = taskManager.listAllTasks();
        assertEquals(2, tasks.size(), "Неверное количество задач");
        assertNotNull(savedSubTask, "Подзадача не создана");
    }

    @Test// Проверка вывода списка всех задач
    public void returnListAllTasks() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 0, 0));
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 2, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newTask(task1);
        taskManager.newEpic(epic1);
        taskManager.newSubTask(subtask1);
        List<Task> createAllTasks = taskManager.listAllTasks();
        assertEquals(3, createAllTasks.size(), "Неверное количество задач");
        assertNotNull(createAllTasks, "Задачи не возвращаются");
        assertNotEquals(0, task1.getId(), "Неверный идентификатор задачи");
    }

    @Test// Проверка вывода с пустым списком всех задач
    public void returnEmptyListAllTasks() {
        taskManager = createManager();
        List<Task> createAllTasks = taskManager.listAllTasks();
        assertEquals(0, createAllTasks.size(), "Список всех задач не пустой");
    }

    @Test// Проверка на неверный идентификатор задачи
    public void returnFailIdTasks() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 0, 0));
        taskManager.newTask(task1);
        final int taskId = task1.getId();
        final Task savedTask = taskManager.getTask(taskId);
        final int returnIdTask = savedTask.getId();
        assertEquals(taskId, returnIdTask, "Неверный или отсутсвует идентификатор задачи");
    }

    @Test// Проверка удаления всех задач
    public void deleteAllTasks() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 0, 0));
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 2, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newTask(task1);
        taskManager.newEpic(epic1);
        taskManager.newSubTask(subtask1);
        taskManager.deleteAllTasks();
        List<Task> createAllTasks = taskManager.listAllTasks();
        assertEquals(0, createAllTasks.size(), "Список задач после удаления не пустой.");
    }

    @Test// Проверка удаления Task
    public void deleteTask() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 0, 0));
        taskManager.newTask(task1);
        final int taskId = task1.getId();
        final Task savedTask = taskManager.getTask(taskId);
        taskManager.deleteTask(taskId);
        final Task taskAfterDelete = taskManager.getTask(taskId);
        assertNotEquals(savedTask, taskAfterDelete, "Задача не удалена.");
    }

    @Test// Проверка удаления Epic
    public void deleteEpic() {
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(epic1);
        final int epicId = epic1.getId();
        final Epic savedEpic = taskManager.getEpic(epicId);
        taskManager.deleteEpic(epicId);
        final Epic epicAfterDelete = taskManager.getEpic(epicId);
        assertNotEquals(savedEpic, epicAfterDelete, "Эпик не удален.");
    }

    @Test// Проверка удаления SubTask
    public void deleteSubTask() {
        Epic epic2 = new Epic("Одеть шапку", "Шапка должна быть весенней", StatusTask.NEW);
        taskManager.newEpic(epic2);
        SubTask subtask2 = new SubTask("Взять перчатки", "Одеть шарфик",
                StatusTask.IN_PROGRESS, getEpicId(epic2), Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newSubTask(subtask2);
        final int subTaskId = subtask2.getId();
        assertNotEquals(0, subTaskId, "Id неверный или отсутствует");
        final SubTask savedSubTask = taskManager.getSubTask(subTaskId);
        assertNotNull(savedSubTask, "Подзадача не найдена.");
        taskManager.deleteSubTask(subTaskId);
        final SubTask subTaskAfterDelete = taskManager.getSubTask(subTaskId);
        assertNotEquals(savedSubTask, subTaskAfterDelete, "Подзадача не удалена.");
    }

    @Test// Проверка обновления Task
    public void returnUpdateTask() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 0, 0));
        Task task2 = new Task("Побыть дома", "Сегодня дождь", StatusTask.NEW,
                Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 05, 0, 0));
        taskManager.newTask(task1);
        final int taskId = task1.getId();
        final Task testTask = taskManager.getTask(taskId);
        assertNotNull(testTask, "Задача не найдена");
        assertNotEquals(0, taskId, "Id неверный или отсутствует");
        taskManager.updateTask(task2, taskId);
        final Task newTask = taskManager.getTask(taskId);
        assertEquals(task2, newTask, "Задача не обновлена.");
    }

    @Test// Проверка обновления Epic
    public void returnUpdateEpic() {
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(epic1);
        final int epicId = epic1.getId();
        assertNotEquals(0, epicId, "Id неверный или отсутствует");
        final Epic testEpic = taskManager.getEpic(epicId);
        assertNotNull(testEpic, "Epic не найден");
        Epic epic2 = new Epic("Одеть шапку", "Шапка должна быть весенней", StatusTask.NEW);
        taskManager.updateEpic(epic2, epicId);
        final Epic savedEpic = taskManager.getEpic(epicId);
        assertEquals(epic2, savedEpic, "Эпик не обновлен.");
    }

    @Test// Проверка обновления SubTask
    public void returnUpdateSubTask() {
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(epic1);
        final int keyEpic = epic1.getId();
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, keyEpic, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newSubTask(subtask1);
        final int subTaskId = subtask1.getId();
        SubTask subtask3 = new SubTask("Взять перчатки", "Одеть шарфик",
                StatusTask.IN_PROGRESS, keyEpic, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 8, 0, 0));
        taskManager.updateSubTask(subtask3, subTaskId);
        final SubTask savedSubTask1 = taskManager.getSubTask(subTaskId);
        assertEquals(subtask3, savedSubTask1, "Подзадача не обновлена.");
    }

    @Test// Проверка обновления статуса Epic
    public void returnChangeStatusEpic() {
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(epic1);
        final int epicId = epic1.getId();
        assertNotEquals(0, epicId, "Id неверный или отсутствует");
        final Epic testEpic = taskManager.getEpic(epicId);
        assertNotNull(testEpic, "Epic не найден");
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, epicId, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newSubTask(subtask1);
        assertEquals(StatusTask.DONE, subtask1.getStatus(), "Статус Epic не обновлен.");
    }

    @Test//Проверка получения подзадач для Эпика
    public void returnSubTaskForEpic() {
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(epic1);
        final int keyEpic = epic1.getId();
        assertNotEquals(0, keyEpic, "Id неверный или отсутствует");
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, keyEpic, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newSubTask(subtask1);
        List<SubTask> testSubTask = taskManager.getEpicSubtasks(keyEpic);
        assertEquals(subtask1, testSubTask.get(0), "Подзадачи не найдены.");
    }

    @Test//Проверка вывода списка приоритетных задач
    public void returnPrioritizedTasks() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 10, 0));
        Task task2 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 11, 31));
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку не брать", StatusTask.DONE, 3,
                Duration.ofMinutes(90), null);
        SubTask subtask2 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 3, Duration.ofMinutes(90), null);
        assertTrue(taskManager.getPrioritizedTasks(taskManager.listTasksAndSubTasks()).isEmpty(),
                "Список приоритетных задач не пустой");
        taskManager.newTask(task1);
        taskManager.newTask(task2);
        taskManager.newEpic(epic1);
        taskManager.newSubTask(subtask1);
        taskManager.newSubTask(subtask2);
        assertEquals(4, taskManager.getPrioritizedTasks(taskManager.listTasksAndSubTasks()).size(),
                "Количество задач в списке не совпадает");
        assertEquals(task2, taskManager.getPrioritizedTasks(taskManager.listTasksAndSubTasks()).get(0),
                "Задачи не совпадают");
    }

    @Test
    public void returnEndTime() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 10, 0));
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку не брать", StatusTask.DONE, 2,
                Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 10, 11, 00));
        taskManager.newTask(task1);
        final int taskId = task1.getId();
        final Task testTask = taskManager.getTask(taskId);
        assertNotNull(testTask.getEndTime(), "Поле endTime пустое");
        assertEquals(LocalDateTime.of(2022, 05, 1, 11, 30),
                testTask.getEndTime(), "EndTime не совпадают");
        taskManager.newEpic(epic1);
        final int epicId = epic1.getId();
        final Epic testEpic = taskManager.getEpic(epicId);
        taskManager.newSubTask(subtask1);
        final int subTaskId = subtask1.getId();
        final SubTask testSubTask = taskManager.getSubTask(subTaskId);
        assertNotNull(testEpic.getEndTime(), "Поле endTime пустое");
        assertEquals(LocalDateTime.of(2022, 05, 10, 12, 30),
                testEpic.getEndTime(), "EndTime не совпадают");
        assertNotNull(testSubTask.getEndTime(), "endTime не пустой");
        assertEquals(LocalDateTime.of(2022, 05, 10, 12, 30),
                testSubTask.getEndTime(), "EndTime не совпадают");
    }

    @Test//
    public void returnTimeCrossingTasks() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 10, 0));
        Task task2 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 11, 31));
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку не брать", StatusTask.DONE, 3,
                Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 10, 10, 0));
        SubTask subtask2 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 3, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 10, 10, 0));
        taskManager.newEpic(epic1);
        taskManager.newTask(task1);
        assertTrue(taskManager.timeCrossingTasks(task2), "Должно вернуться true");
        taskManager.newTask(subtask1);
        assertEquals(false, taskManager.timeCrossingTasks(subtask2),
                "Пересечения времени не должно быть");
    }
}