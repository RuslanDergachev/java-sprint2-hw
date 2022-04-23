package test;

import historyTasks.InMemoryHistoryManager;
import managerTasks.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    private InMemoryTaskManager taskManager;
    private Epic epic;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
        epic = new Epic("TestAddNewEpic", "TestAddNewEpic description",
                StatusTask.NEW);
        taskManager.newEpic(epic);
    }

    @Test //проверка на пустой список подзадач
    public void returnEmptyListSubTask() {
        final int epicId = epic.getId();
        final Epic savedEpic = taskManager.getEpic(epicId);
        assertNotNull(savedEpic, "EPIC не найден.");
        assertEquals(epic,savedEpic, "EPICS не совпадают.");
        final ArrayList<SubTask> subTasks = taskManager.getEpicSubtasks(epicId);
        assertEquals(0, subTasks.size(), "У вновь созданного EPIC не должно быть SUBTASK.");
        assertEquals(StatusTask.NEW, savedEpic.getStatus(), "Статус у нового Epic должен быть NEW");
    }

    @Test //проверка на все подзадачи со статусом NEW
    public void returnStatusNewForALlSubtasksNew(){
        final int epicId = epic.getId();
        final Task savedEpic = taskManager.getEpic(epicId);

        SubTask subTask1 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.NEW, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        SubTask subTask2 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.NEW, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        taskManager.newSubTask(subTask1);
        taskManager.newSubTask(subTask2);
        StatusTask statusEpic = savedEpic.getStatus();
        assertEquals(StatusTask.NEW, statusEpic, "Статус эпика не совпадает со статусом подзадачи");
    }

    @Test //проверка на все подзадачи со статусом DONE
    public void returnStatusDoneForALlSubtasksDone(){
        final int epicId = epic.getId();
        final Task savedEpic = taskManager.getEpic(epicId);

        SubTask subTask1 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.DONE, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        SubTask subTask2 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.DONE, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        taskManager.newSubTask(subTask1);
        taskManager.newSubTask(subTask2);

        StatusTask statusEpic = savedEpic.getStatus();
        assertEquals(StatusTask.DONE, statusEpic, "Статус эпика должен быть DONE");
    }

    @Test //Проверка на изменение статуса Эпика на IN_PROGRESS, если статусы подзадач разные
    public void returnStatusNewForSubtasksDoneAndNew(){
        final int epicId = epic.getId();
        SubTask subTask1 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.DONE, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        SubTask subTask2 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.NEW, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,04,0,0));
        taskManager.newSubTask(subTask1);
        taskManager.newSubTask(subTask2);
        final Task savedEpic = taskManager.getEpic(epicId);
        StatusTask statusEpic = savedEpic.getStatus();
        assertEquals(StatusTask.IN_PROGRESS, statusEpic,"Статус эпика должен быть IN_PROGRESS");
    }

    @Test //Проверка статуса эпика. Должен быть IN_PROGRESS, если у подзадач IN_PROGRESS
    public void returnStatusInProgressForSubtasksInProgress(){
        final int epicId = epic.getId();
        final Task savedEpic = taskManager.getEpic(epicId);

        SubTask subTask1 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.IN_PROGRESS, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        SubTask subTask2 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.IN_PROGRESS, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,03,0,0));
        taskManager.newSubTask(subTask1);
        taskManager.newSubTask(subTask2);

        StatusTask statusEpic = savedEpic.getStatus();
        assertEquals(StatusTask.IN_PROGRESS, statusEpic, "Статус эпика должен быть IN_PROGRESS");
    }
    @Test //Проверка расчета полей Duration и StartTime, endTime
    public void returnStartTimeAndDurationAndEndTimeForEpic(){
        final int epicId = epic.getId();
        final Task saveEpic = taskManager.getEpic(epicId);
        assertNull(saveEpic.getDuration(), "DURATION должен отсутствовать");
        assertNull(saveEpic.getStartTime(), "startTime должен отсутствовать");
        assertNull(saveEpic.getEndTime(), "endTime должен отсутствовать");

        SubTask subTask1 = new SubTask("TestAddNewSubTask", "TestAddNewSubTask description",
                StatusTask.IN_PROGRESS, epicId, Duration.ofMinutes(90), LocalDateTime.of(2022,05,01,0,0));
        taskManager.newSubTask(subTask1);
        final Task savedEpic = taskManager.getEpic(epicId);
        assertEquals(Duration.ofMinutes(90), savedEpic.getDuration(), "DURATION не совпадает");
        assertNotNull(savedEpic.getDuration(), "DURATION не пустой");
        assertEquals(LocalDateTime.of(2022,05,01,0,0), savedEpic.getStartTime(), "startTime не совпадает");
        assertNotNull(savedEpic.getStartTime(), "startTime не пустой");
        assertEquals(LocalDateTime.of(2022,05,01,1,30), savedEpic.getEndTime(), "endTime не совпадает");
        assertNotNull(savedEpic.getEndTime(), "endTime не пустой");
    }
    @Test//Проверка сеттеров для StartTime, Duration, EndTime
    public void returnSetStartTimeAndDurationAndEndTimeForEpic(){
        final int epicId = epic.getId();
        epic.setDuration(Duration.ofMinutes(90));
        final Task saveEpic = taskManager.getEpic(epicId);
        assertEquals(Duration.ofMinutes(90), saveEpic.getDuration(), "DURATION не совпадает");
        epic.setStartTime(LocalDateTime.of(2022,05,01,0,0));
        assertEquals(LocalDateTime.of(2022,05,01,0,0), saveEpic.getStartTime(), "StartTime не совпадает");
        epic.setEndTime(LocalDateTime.of(2022,05,01,1,30));
        assertEquals(LocalDateTime.of(2022,05,01,1,30), saveEpic.getEndTime(), "EndTime не совпадает");

    }
}