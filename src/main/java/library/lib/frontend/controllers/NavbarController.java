package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

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

    @Override
    protected Node getStage() {
        return booksListNav;
    }
}
