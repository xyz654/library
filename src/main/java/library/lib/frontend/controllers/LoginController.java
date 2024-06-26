package library.lib.frontend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.ReturnCodes;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.services.BookService;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginController extends BaseController {

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
    private void onLoginClick(){
        String password = passwordField.getText();
        String email = emailField.getText();

        ReturnModel model = service.login(email, password);
        if (model.code == ReturnCodes.OK) {
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

        redirectToScene("/library/lib/book-list-view.fxml", "Hello", (Stage) getStage().getScene().getWindow());
    }

    @Override
    protected Node getStage() {
        return loginButton;
    }
}
