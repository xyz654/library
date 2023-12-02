package library.lib.Library.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.Backend.models.Member;
import library.lib.Library.Utils.Validator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static library.lib.Library.Utils.Service.buildParameters;

public class RegisterController {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onRegisterClick() throws IOException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        if (!Validator.validateUsername(username)) {
            errorMessage.setText("Username should only contain alphanumeric characters and underscores");
            errorMessage.setOpacity(1);
            return;
        }

        if (!Validator.validateEmail(email)) {
            errorMessage.setText("Invalid email");
            errorMessage.setOpacity(1);
            return;
        }

        if (!Validator.validatePassword(password)) {
            errorMessage.setText("Password should have at least 8 characters, including uppercase, lowercase, and a number");
            errorMessage.setOpacity(1);
            return;
        }

        Map<Object, Object> data = new HashMap<>();

        data.put("username", username);
        data.put("password", password);
        data.put("email", email);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/register" + buildParameters(data)))
                .POST(
                        HttpRequest.BodyPublishers.ofString("")
                )
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200){
            Member member = new ObjectMapper().readValue(response.body(), Member.class);
            System.out.println(member);
            redirectToLogin();
        }else{
            errorMessage.setOpacity(1);
        }
    }
}