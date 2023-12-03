package library.lib.frontend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.lib.LibraryApp;
import library.lib.backend.models.Member;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.SpringContext;
import library.lib.frontend.utils.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.ResourceBundle;

@Slf4j
@Component
public abstract class BaseController implements Initializable {

//    private ConfigurableApplicationContext springContext;
    protected void redirectToScene(String scenePath, String title, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            ConfigurableApplicationContext springContext = SpringContext.getInstance().getContext();
            loader.setControllerFactory(springContext::getBean);
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

    protected void handleSuccessfulLogin(Member member) throws IOException {
        log.info(String.valueOf(member));
        redirectToScene("/library/lib/dashboard-view.fxml", "Hello", (Stage) getStage().getScene().getWindow());
    }

    protected abstract Node getStage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        springContext = SpringApplication.run(LibraryApp.class);
//        springContext.getAutowireCapableBeanFactory().autowireBean(this);

    }
}
