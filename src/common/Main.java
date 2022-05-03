package common;

import com.google.gson.*;
import http.HttpTaskServer;
import http.KVServer;
import managerTasks.TaskManager;
import tasks.StatusTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        TaskManager taskManager = Managers.getDefault();
        Gson gson = new Gson();

        Task taskTask = new Task("Пойти гулять", "Сегодня хорошая погода"
                , StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 01, 0, 0));
//        //taskManager.newTask(taskTask);
//
//        Task taskTask2 = new Task("Побыть дома", "Сегодня дождь", StatusTask.NEW
//                , Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 01, 0, 0));
//        taskManager.newTask(taskTask2);
//
//        Epic taskEpic7 = new Epic("Пойти побегать", "Стадион рядом", StatusTask.NEW);
//        taskManager.newEpic(taskEpic7);
//
//        Epic taskEpic5 = new Epic("Одеть шапку", "шапка должна быть весенней",
//                StatusTask.NEW);
//        taskManager.newEpic(taskEpic5);
//
//        SubTask taskSubTask = new SubTask("Взять сумку", "Сумку взять маленькую",
//                StatusTask.DONE, 3, Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 01, 0, 0));
//        taskManager.newSubTask(taskSubTask);
//
//        SubTask taskSubTask3 = new SubTask("Взять перчатки", "Одеть шарфик",
//                StatusTask.IN_PROGRESS, 3, Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 01, 0, 0));
//        taskManager.newSubTask(taskSubTask3);
//
//
//        System.out.println(taskManager.getTask(1));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getTask(2));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getTask(2));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getTask(1));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getTask(1));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getEpic(3));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getEpic(4));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getSubTask(6));
//        System.out.println("Проверка истории: " + taskManager.getHistory());
//        System.out.println(taskManager.getSubTask(5));
//        System.out.println("Проверка истории: " + taskManager.getHistory() + '\n');
//
//        System.out.println("История просмотра задач:");
//        taskManager.printAllHistory(taskManager.getHistory());
//        System.out.println("Приоритетные задачи:");
//        System.out.println(taskManager.getPrioritizedTasks(taskManager.listTasksAndSubTasks()));

        new KVServer().start();
        URI kvServerUrl = URI.create("http://localhost:8078/");
        HttpClient newClient = HttpClient.newHttpClient();
        HttpRequest newRequest = HttpRequest.newBuilder()
                .uri(kvServerUrl) // указываем адрес ресурса
                .GET()
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> newResponse = newClient.send(newRequest, HttpResponse.BodyHandlers.ofString());

        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.startServer();//Запуск сервера?
        HttpClient client = HttpClient.newHttpClient();

        URI url = URI.create("http://localhost:8080/tasks/task");

        String json = gson.toJson(taskTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        //создайте объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url) // указываем адрес ресурса
                .POST(body)
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // передаем парсеру тело ответа в виде строки, содержащей данные в формате JSON
            JsonElement jsonElement = JsonParser.parseString(response.body());
            Task newTask = gson.fromJson(jsonElement, Task.class);
            if (!jsonElement.isJsonObject()) { // проверяем, точно ли мы получили JSON-объект
                System.out.println("Ответ от сервера не соответствует ожидаемому.");
                return;
            }
            System.out.println(newTask);
        }

        URI url2 = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request2 = HttpRequest.newBuilder()
                .GET()    // указываем HTTP-метод запроса
                .uri(url2) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client2 = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler2 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response2 = client2.send(request2, handler2);

        if (response2.statusCode() == 200) {
            JsonElement jsonElement = JsonParser.parseString(response2.body());
            if (jsonElement.isJsonObject()) { // проверяем, является ли элемент JSON-объектом
                JsonObject jsonObject = jsonElement.getAsJsonObject();
            } else {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                ArrayList<Task> listTask = new ArrayList<>();
                Iterator<JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JsonElement next = iterator.next();
                    System.out.println(gson.fromJson(next, Task.class));
                }
            }
        }

        URI url3 = URI.create("http://localhost:8080/tasks/task?id=1");

        HttpRequest request3 = HttpRequest.newBuilder()
                .DELETE()    // указываем HTTP-метод запроса
                .uri(url3) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client3 = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler3 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response3 = client3.send(request3, handler3);
        System.out.println(response3);
    }
}

