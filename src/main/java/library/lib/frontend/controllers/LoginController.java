package library.lib.frontend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Member;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
@Component
public class LoginController extends BaseController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MemberService service;

    @FXML
    private TextField emailField;

    @FXML
    private Text errorMessage;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text signUpLink;


    @FXML
    private void onKeyPressed() {
        hideErrorMessage();
    }

    @FXML
    private void redirectToSignUp() {
        redirectToScene("/library/lib/register-view.fxml", "Login", (Stage) loginButton.getScene().getWindow());
    }

    @FXML
    private void onLoginClick() throws IOException, InterruptedException {
        String password = passwordField.getText();
        String email = emailField.getText();

        ReturnModel model = service.login(email, password);
        if (model.code == 200) {
            System.out.println("Success");
            handleSuccessfulLogin((Member) model.object);
        } else {
            showErrorMessage(model.message);
        }
    }

    private void hideErrorMessage() {
        if (errorMessage.getOpacity() != 0) {
            errorMessage.setOpacity(0);
        }
    }

    private void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setOpacity(1);
    }

    @Override
    public void handleSuccessfulLogin(Member loggedInUser) {

        UserState.getInstance().setLoggedInUser(loggedInUser);

        redirectToScene("/library/lib/dashboard-view.fxml", "Hello", (Stage) getStage().getScene().getWindow());
    }

    @Override
    protected Node getStage() {
        return loginButton;
    }
}
