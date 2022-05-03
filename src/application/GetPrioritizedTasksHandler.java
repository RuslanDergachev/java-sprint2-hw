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

public class GetPrioritizedTasksHandler implements HttpHandler {
    TaskManager taskManager;
    Gson gson = new Gson();

    public GetPrioritizedTasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        String path = httpExchange.getRequestURI().getPath();
        List<Task> tasks = taskManager.listTasksAndSubTasks();
        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks(tasks);
        System.out.println(tasks);
        System.out.println(prioritizedTasks);

        if (path.endsWith("/tasks")) {
            if (!prioritizedTasks.isEmpty()) {
                response = gson.toJson(prioritizedTasks);
                System.out.println(response);

                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
            }
            httpExchange.sendResponseHeaders(400, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Список приоритетных задач отсутствует".getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
