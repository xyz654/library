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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static library.lib.Library.Utils.Service.buildParameters;

public class LoginController {

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
        if (errorMessage.getOpacity() != 0) {
            errorMessage.setOpacity(0);
        }
    }

    @FXML
    private void redirectToSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/register-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onLoginClick() throws IOException, InterruptedException{
        String password = passwordField.getText();
        String email = emailField.getText();

        Map<Object, Object> data = new HashMap<>();

        data.put("password", password);
        data.put("email", email);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/login" + buildParameters(data)))
                .POST(
                        HttpRequest.BodyPublishers.ofString("")
                )
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        System.out.println(response.statusCode());
        if(response.statusCode() == 200){
            Member member = new ObjectMapper().readValue(response.body(), Member.class);
            System.out.println(member);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/dashboard-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hello");
        }
        else{
            errorMessage.setOpacity(1);
        }
    }

}