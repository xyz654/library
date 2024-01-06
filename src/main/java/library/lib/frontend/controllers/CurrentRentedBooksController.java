package library.lib.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.backend.models.ReadingRoom;
import library.lib.backend.services.EmailSenderService;
import library.lib.backend.services.ReadingRoomService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CurrentRentedBooksController extends BaseController {

    @FXML
    private ListView<ReadingRoom> bookListView;

    @Autowired
    private ReadingRoomService readingRoomService;

    @Autowired
    private EmailSenderService emailSenderService;

    private static final double IMAGE_FIT_WIDTH = 200;
    private static final double TEXT_FONT_SIZE = 24;
    private static final Insets TEXT_MARGIN = new Insets(0, 0, 0, 50);
    private static final Insets PANE_PADDING = new Insets(50);
    private static final String UNDEFINED_CATEGORY = "undefined";
    private static final String TEXT_FILL_COLOR = "#333333";
    private static final String ODD_BACKGROUND_COLOR = "#b97a57";
    private static final String EVEN_BACKGROUND_COLOR = "#f4f4f4";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (checkIfWorker())
            displayBooksAsWorker();
        else
            displayBooks();
        setupListView();
    }
    private boolean checkIfWorker(){
        Member member = UserState.getInstance().getLoggedInUser();
        return member.getPermission() == Permissions.WORKER;
    }
    private void displayBooks() {
        List<ReadingRoom> readingRooms = readingRoomService.getCurrentRentedBooksByMember(UserState.getInstance().getLoggedInUser());
        ObservableList<ReadingRoom> observableReadingRooms = FXCollections.observableArrayList(readingRooms);
        bookListView.setItems(observableReadingRooms);
    }

    private void displayBooksAsWorker() {
        List<ReadingRoom> readingRooms = readingRoomService.getCurrentRentedBooksAllMembers();
        ObservableList<ReadingRoom> observableReadingRooms = FXCollections.observableArrayList(readingRooms);
        bookListView.setItems(observableReadingRooms);
    }

    private void setupListView() {
        bookListView.setStyle("-fx-background-color: transparent;");
        bookListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bookListView.setCellFactory(createListCellFactory());
    }

    private Callback<ListView<ReadingRoom>, ListCell<ReadingRoom>> createListCellFactory() {
        return param -> new CustomListCell();
    }

    private class CustomListCell extends ListCell<ReadingRoom> {
        @Override
        protected void updateItem(ReadingRoom item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                setGraphic(createReadingRoomCell(item, getIndex()));
            } else {
                setGraphic(null);
            }
        }
    }

    private GridPane createReadingRoomCell(ReadingRoom item, int index) {
        GridPane gridPane = new GridPane();

        ImageView imageView = new ImageView(new Image(item.getBook().getBookCover()));
        imageView.setFitWidth(IMAGE_FIT_WIDTH);
        imageView.setPreserveRatio(true);
        gridPane.add(imageView, 0, 0);

        Text text = new Text(
                "Title: " + item.getBook().getTitle() +
                        "\nAuthor: " + item.getBook().getAuthor().getName() +
                        "\nCategory: " + (item.getBook().getCategory() != null ? item.getBook().getCategory() : UNDEFINED_CATEGORY) +
                        "\nRent Date: " + (item.getStart_date())
        );
        text.setStyle("-fx-font-size: " + TEXT_FONT_SIZE + "px; -fx-fill: " + TEXT_FILL_COLOR + ";");
        GridPane.setMargin(text, TEXT_MARGIN);
        gridPane.add(text, 1, 0);

        String backgroundColor = (index % 2 == 0) ? ODD_BACKGROUND_COLOR : EVEN_BACKGROUND_COLOR;
        gridPane.setStyle("-fx-padding: " + PANE_PADDING + "; -fx-background-color: " + backgroundColor + ";");

        Button returnButton = new Button("Return Book");
        returnButton.setStyle(
                "-fx-background-color: #FF0000; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-cursor: hand;"
        );
        returnButton.setOnAction(event -> returnBookClicked(item.getBook()));
        gridPane.add(returnButton, 2, 0);

        if(checkIfWorker()) {
            Button sendReminderButton = new Button("Email Reminder");
            sendReminderButton.setStyle(
                    "-fx-background-color: #FF0000; " +
                            "-fx-text-fill: #FFFFFF; " +
                            "-fx-cursor: hand;"
            );
            sendReminderButton.setOnAction(event -> emailSenderService.sendBookReminderMail(item.getBook()));
            gridPane.add(sendReminderButton, 2, 0);
        }
        return gridPane;
    }

    private void returnBookClicked(Book book) {
        Member user = UserState.getInstance().getLoggedInUser();
        readingRoomService.returnBook(book, user);
        displayBooks();
        setupListView();
    }

    @Override
    protected Node getStage() {
        return null;
    }
}
