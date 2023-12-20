package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
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

    @Autowired
    MemberService service;

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
            for(Member member: members){
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
