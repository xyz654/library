package library.lib.frontend.controllers;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

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
    private void redirectToAddCategory(){
        redirectToScene("/library/lib/add-new-category.fxml", "Add new author", (Stage) addAuthorButton.getScene().getWindow());
    }
    @FXML
    private void redirectToDashboard(){
        redirectToScene("/library/lib/dashboard-view.fxml", "Dashboard", (Stage) dashboardButton.getScene().getWindow());
    }
    @Override
    protected Node getStage() {
        return null;
    }
}
