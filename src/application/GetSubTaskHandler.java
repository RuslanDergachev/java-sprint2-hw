package application;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managerTasks.TaskManager;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GetSubTaskHandler extends Handler implements HttpHandler {
    TaskManager taskManager;
    Gson gson = new Gson();

    public GetSubTaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        String path = httpExchange.getRequestURI().getPath();
        List<SubTask> subTask;
        Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
        int id = 0;
        if (params != null) {
            id = Integer.parseInt(params.get("id"));
        }
        String method = httpExchange.getRequestMethod();

        switch (method) {
            case "GET":
                subTask = taskManager.getEpicSubtasks(id);
                if (path.endsWith("/subtask") && subTask.size() != 0 && id == 0) {
                    response = gson.toJson(subTask);
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                    break;
                }

                if (id != 0) {
                    if (taskManager.getSubTask(id) != null) {
                        response = gson.toJson(taskManager.getSubTask(id));
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    }
                }
                httpExchange.sendResponseHeaders(400, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write("Подзадачи с таким номером не существует".getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "POST":
                InputStream inputStream = httpExchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                SubTask newSubTask = gson.fromJson(reader.readLine(), SubTask.class);
                if (taskManager.getSubTask(id) == null) {
                    taskManager.newSubTask(newSubTask);
                    response = gson.toJson(taskManager.getSubTask(newSubTask.getId()));
                } else {
                    taskManager.updateSubTask(newSubTask, id);
                    response = gson.toJson(taskManager.getSubTask(id));
                }

                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "DELETE":
                if (id != 0) {
                    taskManager.deleteSubTask(id);
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write("Задача удалена".getBytes(StandardCharsets.UTF_8));
                    }
                }
        }
    }
}
