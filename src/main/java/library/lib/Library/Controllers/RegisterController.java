package library.lib.Library.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.Library.Utils.Validator;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class RegisterController extends BaseController {

    private static final String REGISTER_API_URL = "http://localhost:8080/api/users/register";

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private Text errorMessage;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text loginLink;

    @FXML
    private void onKeyPressed() {
        if (errorMessage.getOpacity() != 0) {
            errorMessage.setOpacity(0);
        }
    }

    @FXML
    private void redirectToLogin() {
        redirectToScene("/library/lib/login-view.fxml",
                "Login", (Stage) registerButton.getScene().getWindow());
    }

    @FXML
    private void onRegisterClick() throws IOException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        if (!Validator.validateUsername(username)) {
            showError("Username should only contain alphanumeric characters and underscores");
            return;
        }

        if (!Validator.validateEmail(email)) {
            showError("Invalid email");
            return;
        }

        if (!Validator.validatePassword(password)) {
            showError("Password should have at least 8 characters, including uppercase, lowercase, and a number");
            return;
        }

        Map<Object, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("email", email);

        HttpResponse<String> response = sendHttpRequest(REGISTER_API_URL, data);
        if (response.statusCode() == 200) {
            handleSuccessfulLogin(response.body());
        } else {
            errorMessage.setOpacity(1);
        }
    }

    @Override
    protected Node getStage() {
        return registerButton;
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setOpacity(1);
    }
}
