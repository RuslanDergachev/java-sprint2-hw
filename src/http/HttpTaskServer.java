package http;

import application.*;
import com.sun.net.httpserver.HttpServer;
import common.Managers;
import managerTasks.TaskManager;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    protected TaskManager tasksManager = Managers.getDefault();
    HttpServer httpServer;

    public void startServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

        httpServer.createContext("/tasks/task", new GetTasksHandler(tasksManager));//получить все задачи (GET)
        httpServer.createContext("/tasks/epic", new GetEpicHandler(tasksManager));//получить эпик (GET)
        httpServer.createContext("/tasks/subtask", new GetSubTaskHandler(tasksManager));//получить подзадачу (GET)
        httpServer.createContext("/tasks/history", new GetHistoryHandler(tasksManager));//получить историю (GET)
        httpServer.createContext("/tasks", new GetPrioritizedTasksHandler(tasksManager));// получить приоритетные задачи
        httpServer.start();
    }

    public void stopServer() {
        httpServer.stop(0);
    }
}

