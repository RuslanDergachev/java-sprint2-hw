package managerTasks;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    public FileBackedTasksManagerTest() {
        taskManager = createManager();
    }

    @Override
    FileBackedTasksManager createManager() {
        File newFile = new File("taskFileTest.csv");
        if (newFile.exists()) {
            newFile.delete();
        }
        try {
            newFile.createNewFile();
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
        return new FileBackedTasksManager("taskFileTest.csv");
    }

    @Test// Проверка сохранения
    public void saveBackedFile() {
        Task task1 = new Task("Пойти гулять", "Сегодня хорошая погода",
                StatusTask.NEW, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 01, 0, 0));
        taskManager.newTask(task1);
        int idTask = task1.getId();
        Epic epic1 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        taskManager.newEpic(epic1);
        final int epicId = epic1.getId();
        List<SubTask> subTasksForEpic = taskManager.getEpicSubtasks(epicId);
        assertEquals(0, subTasksForEpic.size(), "У Эпика не может быть подзадач");
        SubTask subtask1 = new SubTask("Взять сумку", "Сумку взять маленькую",
                StatusTask.DONE, 2, Duration.ofMinutes(90),
                LocalDateTime.of(2022, 05, 07, 0, 0));
        taskManager.newSubTask(subtask1);
        final int subTaskId = subtask1.getId();
        List<SubTask> subTasksForEpic1 = taskManager.getEpicSubtasks(epicId);
        assertEquals(1, subTasksForEpic1.size(), "Количество подзадач не совпадает");
        List<Task> allTasks = taskManager.listAllTasks();
        assertEquals(3, allTasks.size(), "Количество задач не совпадает.");
        assertNotNull(allTasks, "Задачи не возвращаются.");
        assertEquals(task1, allTasks.get(0), "Задачи не совпадают");
        List<Task> emptyListHistoryTaskFromFile = taskManager.getHistory();
        assertEquals(0, emptyListHistoryTaskFromFile.size(), "В истории не должно быть записей.");
        taskManager.getTask(idTask);
        taskManager.getEpic(epicId);
        taskManager.getSubTask(subTaskId);
        List<Task> newListHistoryTaskFromFile = taskManager.getHistory();
        assertNotNull(newListHistoryTaskFromFile, "История задач не возвращается.");
        assertEquals(task1, newListHistoryTaskFromFile.get(0), "Задачи не совпадают");
        assertEquals(3, newListHistoryTaskFromFile.size(), "Количество задач в истории не совпадает.");
    }

}