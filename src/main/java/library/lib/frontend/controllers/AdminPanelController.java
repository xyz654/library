package library.lib.frontend.controllers;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.frontend.state.UserState;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AdminPanelController extends BaseController {

    @FXML
    Button addBookButton;

    @FXML
    Button addAuthorButton;

    @FXML
    Button manageMembersButton;

    @FXML
    Button dashboardButton;
    @FXML
    private void redirectToMemberList(){
        redirectToScene("/library/lib/members-list-view.fxml", "Members", (Stage) manageMembersButton.getScene().getWindow());
    }
    @FXML
    private void redirectToAddBook(){
        redirectToScene("/library/lib/add-new-book.fxml", "Add new book", (Stage) addBookButton.getScene().getWindow());
    }
    @FXML
    private void redirectToAddAuthor(){
        redirectToScene("/library/lib/add-new-author.fxml", "Add new author", (Stage) addAuthorButton.getScene().getWindow());
    }
    @FXML
    private void redirectToDashboard(){
        redirectToScene("/library/lib/dashboard-view.fxml", "Dashboard", (Stage) dashboardButton.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Member member = UserState.getInstance().getLoggedInUser();
        if (member.getPermission() == Permissions.WORKER) {
            manageMembersButton.setOpacity(0);
        } else {
            manageMembersButton.setOpacity(1);
        }
    }
    @Override
    protected Node getStage() {
        return null;
    }
}
