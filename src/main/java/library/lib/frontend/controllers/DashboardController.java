package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import library.lib.backend.models.Member;
import library.lib.frontend.state.UserState;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends BaseController {

    @FXML
    private Text helloText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
    }

    private void displayUsername() {
        Member loggedInUser = UserState.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            helloText.setText("Hello " + loggedInUser.getName() + "!");
        }
    }

    @Override
    protected Node getStage() {
        return helloText;
    }
}
