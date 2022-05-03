package application;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managerTasks.TaskManager;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GetTasksHandler extends Handler implements HttpHandler {
    TaskManager taskManager;
    Gson gson = new Gson();

    public GetTasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        String path = httpExchange.getRequestURI().getPath();
        List<Task> task;
        Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
        int id = 0;
        if (params != null) {
            id = Integer.parseInt(params.get("id"));
        }
        String method = httpExchange.getRequestMethod();

        switch (method) {
            case "GET":
                task = taskManager.listAllTasks();
                if (path.endsWith("/task") && task.size() != 0 && id == 0) {
                    response = gson.toJson(task);
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                    break;
                }

                if (id != 0) {
                    if (taskManager.getTask(id) != null) {
                        response = gson.toJson(taskManager.getTask(id));
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    }
                }
                httpExchange.sendResponseHeaders(400, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write("Задачи с таким номером не существует".getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "POST":
                InputStream inputStream = httpExchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Task newTask = gson.fromJson(reader.readLine(), Task.class);
                if (newTask.getId() == 0) {
                    taskManager.newTask(newTask);
                } else {
                    taskManager.updateTask(newTask, id);
                }
                response = gson.toJson(taskManager.getTask(newTask.getId()));
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "DELETE":
                if (id == 0) {
                    taskManager.deleteAllTasks();
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write("Все задачи удалены".getBytes(StandardCharsets.UTF_8));
                    }
                }
                taskManager.deleteTask(id);
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write("Задача удалена".getBytes(StandardCharsets.UTF_8));
                }
        }
    }
}