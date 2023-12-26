package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.lib.backend.models.Author;
import library.lib.backend.models.ReturnCodes;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddNewAuthorController extends BaseController {

    @Autowired
    AuthorService authorService;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nickField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button goToAdminPanel;

    @FXML
    private void addAuthorClick(){
        String name = nameField.getText();
        String nick = nickField.getText();
        String description = descriptionArea.getText();
        Author author = new Author(name, nick, description);
        ReturnModel authorModel = authorService.addAuthor(author);
        if(authorModel.code == ReturnCodes.OK){
            redirectToScene("/library/lib/add-new-book.fxml", "Add new book", (Stage) goToAdminPanel.getScene().getWindow());
        }
    }

    @FXML
    public void goToAdminPanel() {
        redirectToScene("/library/lib/admin-view.fxml", "Books", (Stage) goToAdminPanel.getScene().getWindow());
    }
    @Override
    protected Node getStage() {
        return null;
    }
}
