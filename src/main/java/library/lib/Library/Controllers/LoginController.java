package library.lib.Library.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginController extends BaseController {

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

        Map<Object, Object> data = buildLoginData(password, email);

        HttpResponse<String> response = sendHttpRequest("http://localhost:8080/api/users/login", data);
        if (response.statusCode() == 200) {
            handleSuccessfulLogin(response.body());
        } else {
            showErrorMessage("Login failed");
        }
    }

    private Map<Object, Object> buildLoginData(String password, String email) {
        Map<Object, Object> data = new HashMap<>();
        data.put("password", password);
        data.put("email", email);
        return data;
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
    protected Node getStage() {
        return loginButton;
    }
}
