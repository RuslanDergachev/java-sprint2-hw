package application;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managerTasks.TaskManager;
import tasks.Epic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


public class GetEpicHandler extends Handler implements HttpHandler {
    TaskManager taskManager;
    Gson gson = new Gson();

    public GetEpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        String path = httpExchange.getRequestURI().getPath();
        Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
        int id = 0;
        if (params != null) {
            id = Integer.parseInt(params.get("id"));
        }
        String method = httpExchange.getRequestMethod();

        switch (method) {
            case "GET":
                if (id != 0) {
                    if (taskManager.getEpic(id) != null) {
                        response = gson.toJson(taskManager.getEpic(id));
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    }
                }
                httpExchange.sendResponseHeaders(400, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write("Эпика с таким номером не существует".getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "POST":
                InputStream inputStream = httpExchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Epic newEpic = gson.fromJson(reader.readLine(), Epic.class);
                if (taskManager.getEpic(id) == null) {
                    taskManager.newEpic(newEpic);
                    response = gson.toJson(taskManager.getEpic(newEpic.getId()));
                } else {
                    taskManager.updateEpic(newEpic, id);
                    response = gson.toJson(taskManager.getEpic(id));
                }

                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
                break;

            case "DELETE":
                taskManager.deleteEpic(id);

                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write("Задача удалена".getBytes(StandardCharsets.UTF_8));
                }
        }
    }
}

