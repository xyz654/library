package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Member;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DashboardController extends BaseController {

    @FXML
    private Text helloText;

    @FXML
    private Text userList;

    @FXML
    private Button allBooksButton;

    @FXML
    private Button currentRentedBooks;

    @Autowired
    MemberService service;

    @FXML
    private void redirectToBooksList() {
        redirectToScene("/library/lib/book-list-view.fxml", "Books", (Stage) allBooksButton.getScene().getWindow());
    }

    @FXML
    private void redirectToCurrentRentedBooks() {
        redirectToScene("/library/lib/current-rented-books-view.fxml", "Books", (Stage) currentRentedBooks.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
    }

    private void displayUsername() {
        Member loggedInUser = UserState.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            helloText.setText("Hello " + loggedInUser.getName() + "!");
            List<Member> members = service.getLatestMembers(5);
            System.out.println(members);
            String text = "Last 5 logged in users: \n";
            for (Member member : members) {
                text += member.getName() + "\n";
            }
            userList.setText(text);

        }
    }

    @Override
    protected Node getStage() {
        return helloText;
    }
}
