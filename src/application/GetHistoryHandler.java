package application;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managerTasks.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetHistoryHandler implements HttpHandler {
    TaskManager taskManager;
    Gson gson = new Gson();

    public GetHistoryHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        String path = httpExchange.getRequestURI().getPath();
        List<Task> tasksHistory = taskManager.getHistory();

        if (path.endsWith("/tasks/history")) {
            if (!tasksHistory.isEmpty()) {
                response = gson.toJson(tasksHistory);

                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
            }
            httpExchange.sendResponseHeaders(400, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("История задач отсутствует".getBytes(StandardCharsets.UTF_8));
            }
        }

    }
}
