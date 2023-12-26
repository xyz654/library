package library.lib.frontend.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;
import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.backend.services.BookService;
import library.lib.backend.services.MemberService;
import library.lib.frontend.state.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class MemberListController extends BaseController {

    @FXML
    private ListView<Member> memberListView;

    @Autowired
    private MemberService memberService;

    private static final double IMAGE_FIT_WIDTH = 200;
    private static final double TEXT_FONT_SIZE = 24;
    private static final Insets TEXT_MARGIN = new Insets(0, 0, 0, 50);
    private static final Insets PANE_PADDING = new Insets(50);
    private static final String TEXT_FILL_COLOR = "#333333";
    private static final String ODD_BACKGROUND_COLOR = "#b97a57";
    private static final String EVEN_BACKGROUND_COLOR = "#f4f4f4";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayMembers();
        setupListView();
    }

    private void displayMembers() {
        List<Member> members = memberService.getAllMembers();
        System.out.println(members.toArray());
        ObservableList<Member> observableMembers = FXCollections.observableArrayList(members);
        memberListView.setItems(observableMembers);
    }

    private void setupListView() {
        memberListView.setStyle("-fx-background-color: transparent;");
        memberListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        memberListView.setCellFactory(createListCellFactory());
    }

    private Callback<ListView<Member>, ListCell<Member>> createListCellFactory() {
        return param -> new CustomListCell();
    }

    private class CustomListCell extends ListCell<Member> {
        @Override
        protected void updateItem(Member item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                setGraphic(createMemberCell(item, getIndex()));
            } else {
                setGraphic(null);
            }
        }
    }

    private GridPane createMemberCell(Member item, int index) {
        GridPane gridPane = new GridPane();

        StringProperty itemPermission = new SimpleStringProperty();

        Region spacer = new Region();
        GridPane.setHgrow(spacer, Priority.ALWAYS);
        gridPane.add(spacer, 1, 0);


        ToggleGroup group = new ToggleGroup();
        RadioButton admin = new RadioButton("admin");
        RadioButton worker = new RadioButton("worker");
        RadioButton member = new RadioButton("member");
        admin.setToggleGroup(group);
        worker.setToggleGroup(group);
        member.setToggleGroup(group);

        if (item.getPermission() == Permissions.MEMBER) {
            member.setSelected(true);
            itemPermission.set("Member");
        }
        if (item.getPermission() == Permissions.WORKER) {
            worker.setSelected(true);
            itemPermission.set("Worker");
        }
        if (item.getPermission() == Permissions.ADMIN) {
            admin.setSelected(true);
            itemPermission.set("Admin");
        }

        worker.setOnAction(event -> {
            memberService.setPermission(item, Permissions.WORKER);
            itemPermission.set("Worker");
        });

        admin.setOnAction(event -> {
            memberService.setPermission(item, Permissions.ADMIN);
            itemPermission.set("Admin");
        });

        member.setOnAction(event -> {
            memberService.setPermission(item, Permissions.MEMBER);
            itemPermission.set("Member");
        });

        gridPane.add(admin, 2, 0);
        gridPane.add(worker, 3, 0);
        gridPane.add(member, 4, 0);
        Text text = new Text();

        text.textProperty().bind(Bindings.createStringBinding(() ->
                        "Name: " + item.getName() +
                                "\n email: " + item.getEmail() +
                                "\nPermission: " + itemPermission.get(),
                itemPermission));

        admin.setStyle("-fx-padding: 10px;");
        worker.setStyle("-fx-padding: 10px;");
        member.setStyle("-fx-padding: 10px;");

        text.setStyle("-fx-font-size: " + TEXT_FONT_SIZE + "px; -fx-fill: " + TEXT_FILL_COLOR + ";");
        GridPane.setMargin(text, TEXT_MARGIN);
        gridPane.add(text, 0, 0);

        String backgroundColor = (index % 2 == 0) ? ODD_BACKGROUND_COLOR : EVEN_BACKGROUND_COLOR;
        gridPane.setStyle("-fx-padding: " + PANE_PADDING + "; -fx-background-color: " + backgroundColor + ";");

        return gridPane;
    }

    @Override
    protected Node getStage() {
        return null;
    }
}
