package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import library.lib.backend.models.Author;
import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.services.AuthorService;
import library.lib.backend.services.BookService;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AddNewBookController extends BaseController{

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @FXML
    private TextField titleField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField linkToBookCoverField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextArea tableOfContentsArea;

    @FXML
    private Button goToAdminPanel;

    @FXML
    private ComboBox<String> authorComboBox;

    @FXML
    private void addBookClick(){
        String title = titleField.getText();
        String category = categoryField.getText();
        String linkToBookCover = linkToBookCoverField.getText();
        String description = descriptionArea.getText();
        String tableOfContents = tableOfContentsArea.getText();
        String authorName = authorComboBox.getValue();

        System.out.println(authorName);
        List <Author> author = authorService.getAuthorByName(authorName);
        Book book = new Book(title, author.get(0), description, linkToBookCover, tableOfContents, category);
        Member member = UserState.getInstance().getLoggedInUser();

        bookService.addBook(book, member);

    }

    @FXML
    public void goToAdminPanel() {
        redirectToScene("/library/lib/admin-view.fxml", "Books", (Stage) goToAdminPanel.getScene().getWindow());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Author> authors = authorService.getAllAuthors();
        List<String> strAuthors = new ArrayList<>();
        for (Author author : authors){
            strAuthors.add(author.getName());
        }
        authorComboBox.getItems().setAll(strAuthors);

    }
    @Override
    protected Node getStage() {
        return null;
    }
}
