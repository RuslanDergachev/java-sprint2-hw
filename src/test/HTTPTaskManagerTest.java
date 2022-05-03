package test;

import com.google.gson.*;
import http.HttpTaskServer;
import http.KVServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;
import util.HTTPTaskServerHelper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPTaskManagerTest {

    URI urlForTasks;
    HttpTaskServer httpTaskServer;
    KVServer kvServer;
    Gson gson = new Gson();

    @BeforeEach
    public void startServer() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.startServer();
    }

    @AfterEach
    public void stopKvServer() {
        kvServer.stopServer();
        httpTaskServer.stopServer();
    }

    @Test//Проверка создания, сохранения, получения и удаления Task
    public void saveAndReturnAndDeleteTasksOnHTTPTaskServer() throws IOException, InterruptedException {
        urlForTasks = URI.create("http://localhost:8080/tasks/task");
        HttpClient client = HttpClient.newHttpClient();

        Task newTask1 = new Task("Пойти гулять", "Сегодня хорошая погода"
                , StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 01, 0, 0));
        String json = gson.toJson(newTask1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        //создайте объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlForTasks) // указываем адрес ресурса
                .POST(body)
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = JsonParser.parseString(response.body());
        List<Task> allTasks = HTTPTaskServerHelper.returnListAllTasks();
        newTask1.setId(1);
        Task testTask = HTTPTaskServerHelper.returnTaskForId(1);
        assertEquals(newTask1, testTask, "Задачи не совпадают");
        assertEquals(1, allTasks.size(), "Количество задач не совпадает");
        assertNotNull(allTasks, "Список задач не может быть пустым");
        HTTPTaskServerHelper.deleteTaskForId(1);
        assertNull(HTTPTaskServerHelper.returnListAllTasks(),"Задача не удалена");
    }

    @Test//Проверка создания, сохранения, получения и удаления Epic
    public void saveAndReturnAndDeleteEpicOnHTTPTaskServer() throws IOException, InterruptedException{
        urlForTasks = URI.create("http://localhost:8080/tasks/epic");
        HttpClient client = HttpClient.newHttpClient();

        Epic testEpic = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        String json = gson.toJson(testEpic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        //создайте объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlForTasks) // указываем адрес ресурса
                .POST(body)
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> allTasks = HTTPTaskServerHelper.returnListAllTasks();

        testEpic.setId(1);

        Epic returnEpic = HTTPTaskServerHelper.returnEpicForId(1);
        assertEquals(testEpic, returnEpic, "Задачи не совпадают");

        assertEquals(1, allTasks.size(), "Количество задач не совпадает");
        assertNotNull(allTasks, "Список задач не может быть пустым");
        HTTPTaskServerHelper.deleteEpicForId(1);
        assertNull(HTTPTaskServerHelper.returnListAllTasks(),"Задача не удалена");
    }

    @Test//Проверка создания, сохранения, получения и удаления SubTask
    public void saveAndReturnAndDeleteSubTaskOnHTTPTaskServer() throws IOException, InterruptedException{
        JsonObject jsonObject = new JsonObject();
        urlForTasks = URI.create("http://localhost:8080/tasks/epic");
        HttpClient client = HttpClient.newHttpClient();

        Epic testEpic = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
        String json = gson.toJson(testEpic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        //создайте объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlForTasks) // указываем адрес ресурса
                .POST(body)
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        URI urlForTasks2 = URI.create("http://localhost:8080/tasks/subtask");

        SubTask testSubTask = new SubTask("Взять перчатки", "Одеть шарфик",
              StatusTask.IN_PROGRESS, 1, Duration.ofMinutes(90)
                , LocalDateTime.of(2022, 05, 01, 0, 0));
        String json1 = gson.toJson(testSubTask);
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json1);
        //создайте объект, описывающий HTTP-запрос
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(urlForTasks2) // указываем адрес ресурса
                .POST(body1)
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        if (response1.statusCode() == 200) {
            JsonElement jsonElement = JsonParser.parseString(response1.body());
            if (jsonElement.isJsonObject()) { // проверяем, является ли элемент JSON-объектом
                jsonObject = jsonElement.getAsJsonObject();
            } else {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                ArrayList<Task> listSubTask = new ArrayList<>();
                Iterator<JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JsonElement next = iterator.next();
                    listSubTask.add(gson.fromJson(next, SubTask.class));
                }
            }
        }
        List<Task> allTasks = HTTPTaskServerHelper.returnListAllTasks();
        testSubTask.setId(2);
        SubTask returnSubTask = HTTPTaskServerHelper.returnSubTaskForId(2);
        assertEquals(testSubTask, returnSubTask, "Задачи не совпадают");

        assertEquals(2, allTasks.size(), "Количество задач не совпадает");
        assertNotNull(allTasks, "Список задач не может быть пустым");
        HTTPTaskServerHelper.deleteSubTaskForId(2);
        assertEquals(1, HTTPTaskServerHelper.returnListAllTasks().size(),"Задача не удалена");
    }

    @Test// Проверка вывода списка приоритетных задач
    public void returnListPrioritizedTasks() throws IOException, InterruptedException{
        HTTPTaskServerHelper.createTask1();
        HTTPTaskServerHelper.createTask2();
        HTTPTaskServerHelper.createEpic();
        HTTPTaskServerHelper.createSubTask();
        List<Task> prioritizedTasks = HTTPTaskServerHelper.returnPrioritizedTasks();
        assertEquals(2,prioritizedTasks.size(),"Количество задач не совпадает");
        assertNotNull(prioritizedTasks.size(), "Список приоритетных задач пустой");
    }
}