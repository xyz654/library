package library.lib.frontend.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.lib.backend.models.Member;
import library.lib.backend.persistence.BookRepository;
import library.lib.frontend.state.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Component
public abstract class BaseController implements Initializable {

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

    protected void handleSuccessfulLogin(Member member) {
        log.info(String.valueOf(member));
        redirectToScene("/library/lib/dashboard-view.fxml", "Dashboard", (Stage) getStage().getScene().getWindow());
    }

    protected abstract Node getStage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
