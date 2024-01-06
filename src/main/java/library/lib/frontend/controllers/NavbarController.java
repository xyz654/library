package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class NavbarController extends BaseController {

    @FXML
    private VBox booksListNav;

    @FXML
    private VBox currentRentedBooksNav;

    @FXML
    private VBox rentedBooksHistoryNav;

    @FXML
    private VBox dashboardNav;

    @FXML
    private VBox adminNav;

    @FXML
    private HBox navbar;


    @FXML
    private void redirectToBooksList() {
        redirectToScene("/library/lib/book-list-view.fxml", "Books", (Stage) booksListNav.getScene().getWindow());
    }

    @FXML
    private void redirectToCurrentRentedBooks() {
        redirectToScene("/library/lib/current-rented-books-view.fxml", "Books", (Stage) currentRentedBooksNav.getScene().getWindow());
    }

    @FXML
    private void redirectToRentedBooksHistory() {
        redirectToScene("/library/lib/rented-books-history-view.fxml", "Books", (Stage) rentedBooksHistoryNav.getScene().getWindow());
    }

    @FXML
    private void redirectToDashboard() {
        redirectToScene("/library/lib/dashboard-view.fxml", "Books", (Stage) dashboardNav.getScene().getWindow());
    }

    @FXML
    private void redirectToAdminPanel() {
        redirectToScene("/library/lib/admin-view.fxml", "Admin panel", (Stage) dashboardNav.getScene().getWindow());
    }

    @FXML
    private void redirectToUserPanel() {
        redirectToScene("/library/lib/profil-view.fxml", "User panel", (Stage) dashboardNav.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Member member = UserState.getInstance().getLoggedInUser();
        if (member.getPermission() == Permissions.MEMBER) {
            navbar.getChildren().remove(adminNav);
        }
    }

    @Override
    protected Node getStage() {
        return booksListNav;
    }
}
