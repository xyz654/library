package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Member;
import library.lib.backend.persistence.MemberRepository;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
public class UserPanelController extends BaseController{

    @Autowired
    MemberService memberService;

    @FXML
    Text titleLabel;

    @FXML
    Text name;

    @FXML
    Text email;

    @FXML
    Button dashboard;

    @FXML
    private ToggleButton notifications;

    @FXML
    private void goToDashboard(){
        redirectToScene("/library/lib/dashboard-view.fxml", "Books", (Stage) dashboard.getScene().getWindow());
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
    }

    @Override
    protected Node getStage() {
        return null;
    }
}
