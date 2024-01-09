package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Category;
import library.lib.backend.models.Member;
import library.lib.backend.services.BookService;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


@Component
public class UserPanelController extends BaseController{


    @Autowired
    BookService bookService;
    @Autowired
    MemberService memberService;

    @FXML
    Text titleLabel;

    @FXML
    Text name;

    @FXML
    Text email;

    @FXML
    Button booklist;

    @FXML
    private ToggleButton notifications;
    @FXML
    private ComboBox<String> categoryComboBox;


    @FXML
    private void goToDashboard(){
        redirectToScene("/library/lib/book-list-view.fxml", "Books", (Stage) booklist.getScene().getWindow());
    }

    @FXML
    private void notificationChange(){
        Member user = UserState.getInstance().getLoggedInUser();
        if (notifications.isSelected()) {
            notifications.setText("On");
            notifications.setStyle("-fx-background-color:  #00ff00;");
            memberService.setNotifications(user, true);
        } else {
            notifications.setText("Off");
            notifications.setStyle("-fx-background-color: #ff0000;");
            memberService.setNotifications(user, false);
        }
    }

    @FXML
    public void saveCategories(){
        Category category = bookService.getCategoryByName(categoryComboBox.getValue());
        Member mem = UserState.getInstance().getLoggedInUser();
        memberService.setFavouriteCategory(mem, category);
        mem.setFavouriteCategory(category);
        UserState.getInstance().setLoggedInUser(mem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Member user = UserState.getInstance().getLoggedInUser();
        name.setText(user.getName());
        email.setText(user.getEmail());
        if(user.checkForNotifications()){
            notifications.setText("On");
            notifications.setStyle("-fx-background-color:  #00ff00;");
            notifications.setSelected(true);
        }
        else{
            notifications.setText("Off");
            notifications.setStyle("-fx-background-color: #ff0000;");
            notifications.setSelected(false);
        }

        List<Category> categories = bookService.getAllCategories();
        List<String> strCategories = new ArrayList<>();
        for (Category category : categories) {
            strCategories.add(category.getName());
        }
        categoryComboBox.getItems().setAll(strCategories);
        categoryComboBox.setValue(user.getFavouriteCategory().getName());

    }

    @Override
    protected Node getStage() {
        return null;
    }
}
