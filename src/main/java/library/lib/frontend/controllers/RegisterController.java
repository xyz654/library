package library.lib.frontend.controllers;

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
import library.lib.frontend.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RegisterController extends BaseController {
    @Autowired
    MemberService service;
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
    private void onKeyPressed() { hideErrorMessage();}

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
            showErrorMessage("Username should only contain alphanumeric characters and underscores");
            return;
        }

        if (!Validator.validateEmail(email)) {
            showErrorMessage("Invalid email");
            return;
        }

        if (!Validator.validatePassword(password)) {
            showErrorMessage("Password should have at least 8 characters, including uppercase, lowercase, and a number");
            return;
        }

        ReturnModel model = service.register(username, password, email);
        if (model.code == 200) {
            handleSuccessfulLogin((Member) model.object);
        } else {
            showErrorMessage(model.message);

        }
    }

    @Override
    public void handleSuccessfulLogin(Member registeredUser) {
        UserState.getInstance().setLoggedInUser(registeredUser);
        redirectToScene("/library/lib/dashboard-view.fxml", "Hello", (Stage) getStage().getScene().getWindow());
    }

    @Override
    protected Node getStage() {
        return registerButton;
    }

    private void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setOpacity(1);
    }

    private void hideErrorMessage() {
        if (errorMessage.getOpacity() != 0) {
            errorMessage.setOpacity(0);
        }
    }
}
