package util;

import com.google.gson.*;
import tasks.Epic;
import tasks.StatusTask;
import tasks.SubTask;
import tasks.Task;

import javax.imageio.IIOException;
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

public class HTTPTaskServerHelper {

    public static Task createTask1() throws IOException, InterruptedException {

        Gson gson = new Gson();
        URI urlForTasks = URI.create("http://localhost:8080/tasks/task");
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
        return gson.fromJson(response.body(), Task.class);
    }

    public static Task createTask2() throws IOException, InterruptedException {

        Gson gson = new Gson();
        URI urlForTasks = URI.create("http://localhost:8080/tasks/task");
        HttpClient client = HttpClient.newHttpClient();

        Task newTask1 = new Task("Пойти гулять", "Сегодня хорошая погода"
                , StatusTask.NEW, Duration.ofMinutes(90), LocalDateTime.of(2022, 05, 02, 15, 0));
        String json = gson.toJson(newTask1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        //создайте объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlForTasks) // указываем адрес ресурса
                .POST(body)
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Task.class);
    }

    public static Epic createEpic() throws IOException, InterruptedException {
        Gson gson = new Gson();
        URI urlForTasks = URI.create("http://localhost:8080/tasks/epic");
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
        return gson.fromJson(response.body(), Epic.class);
    }

    public static SubTask createSubTask() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpClient client = HttpClient.newHttpClient();
        Epic newEpic = createEpic();
        JsonObject jsonObject;

        URI urlForTasks2 = URI.create("http://localhost:8080/tasks/subtask");

        SubTask testSubTask = new SubTask("Взять перчатки", "Одеть шарфик",
                StatusTask.IN_PROGRESS, newEpic.getId(), Duration.ofMinutes(90)
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
                return gson.fromJson(jsonObject, SubTask.class);
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
        return null;
    }

    public static List<Task> returnListAllTasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
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
                    listTask.add(gson.fromJson(next, Task.class));
                }
                return listTask;
            }
        }
        return null;
    }

    public static Task returnTaskForId(int id) throws IOException, InterruptedException {
        Gson gson = new Gson();
        int newId = id;
        URI url3 = URI.create("http://localhost:8080/tasks/task?id=" + newId);

        HttpRequest request3 = HttpRequest.newBuilder()
                .GET()    // указываем HTTP-метод запроса
                .uri(url3) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client3 = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler3 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response3 = client3.send(request3, handler3);
        System.out.println(response3.body());
        JsonElement jsonElement = JsonParser.parseString(response3.body());
        return gson.fromJson(jsonElement, Task.class);

    }

    public static void deleteTaskForId(int id) throws IOException, InterruptedException {
        Gson gson = new Gson();
        int newId = id;
        URI url3 = URI.create("http://localhost:8080/tasks/task?id=" + newId);

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()    // указываем HTTP-метод запроса
                .uri(url3) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response3 = client.send(request, handler);

    }

    public static Epic returnEpicForId(int id) throws IOException, InterruptedException {
        Gson gson = new Gson();
        int newId = id;
        URI url = URI.create("http://localhost:8080/tasks/epic?id=" + newId);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()    // указываем HTTP-метод запроса
                .uri(url) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client3 = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler3 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client3.send(request, handler3);
        JsonElement jsonElement = JsonParser.parseString(response.body());
        return gson.fromJson(jsonElement, Epic.class);
    }

    public static void deleteEpicForId(int id) throws IOException, InterruptedException {
        Gson gson = new Gson();
        int newId = id;
        URI url3 = URI.create("http://localhost:8080/tasks/epic?id=" + newId);

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()    // указываем HTTP-метод запроса
                .uri(url3) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
    }

    public static SubTask returnSubTaskForId(int id) throws IOException, InterruptedException {
        Gson gson = new Gson();
        int newId = id;
        URI url = URI.create("http://localhost:8080/tasks/subtask?id=" + newId);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()    // указываем HTTP-метод запроса
                .uri(url) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client3 = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler3 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client3.send(request, handler3);
        JsonElement jsonElement = JsonParser.parseString(response.body());
        return gson.fromJson(jsonElement, SubTask.class);
    }

    public static void deleteSubTaskForId(int id) throws IOException, InterruptedException {
        Gson gson = new Gson();
        int newId = id;
        URI url3 = URI.create("http://localhost:8080/tasks/subtask?id=" + newId);

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()    // указываем HTTP-метод запроса
                .uri(url3) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
    }

    public static List<Task> returnPrioritizedTasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()    // указываем HTTP-метод запроса
                .uri(url) // указываем адрес ресурса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Accept", "application/json") // указываем заголовок Accept
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpClient client3 = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler3 = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client3.send(request, handler3);
        if (response.statusCode() == 200) {
            JsonElement jsonElement = JsonParser.parseString(response.body());
            if (jsonElement.isJsonObject()) { // проверяем, является ли элемент JSON-объектом
                JsonObject jsonObject = jsonElement.getAsJsonObject();
            } else {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                ArrayList<Task> listTask = new ArrayList<>();
                Iterator<JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JsonElement next = iterator.next();
                    listTask.add(gson.fromJson(next, Task.class));
                }
                return listTask;
            }
        }
        return null;

    }
}
