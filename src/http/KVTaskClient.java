package http;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new Gson();
    String API_KEY;

    protected URI url;

    public KVTaskClient(URI url) {
        this.url = url;
        registration();
    }

    public void registration() {
        try {
            URI urlRegistration = URI.create(url.toASCIIString() + "register");
            //создайте объект, описывающий HTTP-запрос
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(urlRegistration) // указываем адрес ресурса
                    .GET()
                    .build(); // заканчиваем настройку и создаём ("строим") http-запрос
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            API_KEY = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void put(String key, String json) throws IOException, InterruptedException {

        URI url2 = URI.create(url.toASCIIString() + "save/" + key + "?API_KEY=" + API_KEY);

        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        //создайте объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url2) // указываем адрес ресурса
                .POST(body)
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Ответ от сервера не соответствует ожидаемому.");
        }
    }

    String load(String key) throws IOException, InterruptedException {
        String taskBody = "";
        URI url3 = URI.create(url.toASCIIString() + "load/" + key + "?API_KEY=" + API_KEY);

        //создайте объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url3) // указываем адрес ресурса
                .GET()
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // передаем парсеру тело ответа в виде строки, содержащей данные в формате JSON
            return response.body();
        } else {
            System.out.println("Ответ от сервера не соответствует ожидаемому.");
        }
        return null;
    }
}
