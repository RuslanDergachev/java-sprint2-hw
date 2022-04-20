package historyTasks;

import managerTasks.InMemoryTaskManager;
import managerTasks.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
            StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01, 0, 0));
    Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
    SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую", StatusTask.DONE, 2,
            Duration.ofMinutes(90), LocalDateTime.of(2022,05,07, 0,0));

    @Test// Проверка с пустым списком истории задач
    public void returnHistoryTask(){
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        List<Task> historyTasks = historyManager.getHistory();
        assertTrue(historyTasks.isEmpty(), "Список истории задач должен быть пустой.");
    }

    @Test// Проверка на дублирование истории задач
    public void returnDoubleHistoryTask(){
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01, 0, 0));
        task1.setId(1);
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        historyManager.add(task1);
        historyManager.add(task1);
        historyManager.add(epic1);
        List<Task> historyTasks = historyManager.getHistory();
        assertEquals(2, historyTasks.size(), "В истории задач не должно быть повторов.");
    }
    @Test//добавление в историю
    void add() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        task1.setId(1);
        historyManager.add(task1);
        final List<Task> historyTask = historyManager.getHistory();
        assertNotNull(historyTask, "История не пустая");
        assertEquals(1, historyTask.size(),"История не пустая");
    }

    @Test// Проверка на удаление из начала списка истории задач
    public void deleteTaskFromStartListHistoryTask(){
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        task1.setId(1);
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        epic1.setId(2);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую", StatusTask.DONE, 2,
                Duration.ofMinutes(90), LocalDateTime.of(2022,05,07,0,0));
        subtask1.setId(3);
        historyManager.add(task1);
        historyManager.add(epic1);
        historyManager.add(subtask1);
        final int taskId = task1.getId();
        historyManager.remove(taskId);
        List<Task> historyTask = historyManager.getHistory();
        assertEquals(2, historyTask.size(),"Неверное количество записей");
        assertEquals(epic1, historyTask.get(0), "Первая запись в истории задач не удалена.");
    }

    @Test// Проверка на удаление из середины списка истории задач
    public void deleteTaskFromCenterListHistoryTask(){
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        task1.setId(1);
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        epic1.setId(2);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую", StatusTask.DONE, 2,
                Duration.ofMinutes(90), LocalDateTime.of(2022,05,07,0,0));
        subtask1.setId(3);
        historyManager.add(task1);
        historyManager.add(epic1);
        historyManager.add(subtask1);
        final int epicId = epic1.getId();
        historyManager.remove(epicId);
        List<Task> historyTasks = historyManager.getHistory();
        assertEquals(2,historyTasks.size(),"Неверное количество записей");
        assertEquals(subtask1, historyTasks.get(1), "Запись в истории задач из середины списка не удалена.");
    }

    @Test// Проверка на удаление из конца списка истории задач
    public void deleteTaskFromEndListHistoryTask(){
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        epic1.setId(2);
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        epic1.setId(2);
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую", StatusTask.DONE, 2,
                Duration.ofMinutes(90), LocalDateTime.of(2022,05,07,0,0));
        subtask1.setId(3);
        historyManager.add(task1);
        historyManager.add(epic1);
        historyManager.add(subtask1);
        final int subTaskId = subtask1.getId();
        historyManager.remove(subTaskId);
        List<Task> historyTasks = historyManager.getHistory();
        assertEquals(2, historyTasks.size(),"Неверное количество записей");
        assertEquals(epic1, historyTasks.get(1), "Запись в конце списка истории задач не удалена.");
    }
}