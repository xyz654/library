package library.lib.Library.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.lib.Backend.models.Member;
import library.lib.Library.Utils.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public abstract class BaseController {

    protected void redirectToScene(String scenePath, String title, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpResponse<String> sendHttpRequest(String apiUrl, Map<Object, Object> data) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + Service.buildParameters(data)))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected void handleSuccessfulLogin(String responseBody) throws IOException {
        Member member = new ObjectMapper().readValue(responseBody, Member.class);
        System.out.println(member);
        redirectToScene("/library/lib/dashboard-view.fxml", "Hello", (Stage) getStage().getScene().getWindow());
    }

    protected abstract Node getStage();
}
