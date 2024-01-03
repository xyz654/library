package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.lib.backend.models.Author;
import library.lib.backend.models.Category;
import library.lib.backend.models.ReturnCodes;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.services.AuthorService;
import library.lib.backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddNewCategoryController extends BaseController {

    @Autowired
    BookService bookService;

    @FXML
    private TextField nameField;


    @FXML
    private Button goToAdminPanel;

    @FXML
    private void addCategoryClick() {
        String name = nameField.getText();

        if(name.isEmpty()){
            return;
        }

        ReturnModel authorModel = bookService.addCategory(name);
        if (authorModel.code == ReturnCodes.OK) {
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
