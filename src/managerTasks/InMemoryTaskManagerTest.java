package managerTasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    public InMemoryTaskManagerTest(){
        taskManager = new InMemoryTaskManager();
    }

    @Test// проверка на генерацию Id
    public void returnId(){

        int newId = taskManager.getId();
        int testId = newId + 1;
        int newTestId = taskManager.getId();
        assertEquals(testId, newTestId, "Генерация Id работает неправильно.");
    }

    @Override
    InMemoryTaskManager createManager() {
        return new InMemoryTaskManager();
    }
}