package test;

import managerTasks.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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