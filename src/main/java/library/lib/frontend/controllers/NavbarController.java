package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class NavbarController extends BaseController {

    @FXML
    private Text booksListNav;

    @FXML
    private Text currentRentedBooksNav;

    @FXML
    private Text rentedBooksHistoryNav;

    @FXML
    private Text dashboardNav;

    @FXML
    private Text adminNav;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Member member = UserState.getInstance().getLoggedInUser();
        if (member.getPermission() != Permissions.MEMBER) {
            adminNav.setOpacity(1);
        } else {
            adminNav.setOpacity(0);
        }
    }

    @Override
    protected Node getStage() {
        return booksListNav;
    }
}
