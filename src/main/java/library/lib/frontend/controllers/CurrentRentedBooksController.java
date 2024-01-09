package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.backend.models.ReadingRoom;
import library.lib.backend.services.EmailSenderService;
import library.lib.backend.services.ReadingRoomService;
import library.lib.frontend.layout.BootstrapColumn;
import library.lib.frontend.layout.BootstrapPane;
import library.lib.frontend.layout.BootstrapRow;
import library.lib.frontend.layout.Breakpoint;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CurrentRentedBooksController extends BaseController {
    @Autowired
    private ReadingRoomService readingRoomService;

    @Autowired
    private EmailSenderService emailSenderService;

    @FXML
    private BorderPane borderPane;

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
    }
    private boolean checkIfWorker(){
        Member member = UserState.getInstance().getLoggedInUser();
        return member.getPermission() == Permissions.WORKER;
    }
    private void displayBooks() {
        List<ReadingRoom> readingRooms = readingRoomService.getCurrentRentedBooksByMember(UserState.getInstance().getLoggedInUser());
        renderBooks(readingRooms);
    }

    private void displayBooksAsWorker() {
        List<ReadingRoom> readingRooms = readingRoomService.getCurrentRentedBooksAllMembers();
        renderBooks(readingRooms);
    }

    private void renderBooks(List<ReadingRoom> readingRooms) {
        BootstrapPane bootstrapPane = new BootstrapPane();
        bootstrapPane.setPadding(new Insets(15));
        bootstrapPane.setVgap(25);
        bootstrapPane.setHgap(25);

        BootstrapRow row = new BootstrapRow();
        for (ReadingRoom readingRoom: readingRooms) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/rented-book.fxml"));
                VBox bookBox = loader.load();
                RentedBookController rentedBookController = loader.getController();
                rentedBookController.setReadingRoom(readingRoom);

                rentedBookController.getReturnBookButton().setOnAction(event -> returnBookClicked(readingRoom.getBook()));
                if (checkIfWorker()) {
                    rentedBookController.getEmailReminderButton().setOnAction(event -> emailSenderService.sendBookReminderMail(readingRoom.getBook()));
                } else {
                    rentedBookController.getEmailReminderButton().setOpacity(0);
                }

                BootstrapColumn column = new BootstrapColumn(bookBox);
                column.setBreakpointColumnWidth(Breakpoint.XSMALL, 6);
                column.setBreakpointColumnWidth(Breakpoint.SMALL, 4);
                column.setBreakpointColumnWidth(Breakpoint.LARGE, 2);
                row.addColumn(column);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        bootstrapPane.addRow(row);
        ScrollPane scrollPane = new ScrollPane(bootstrapPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        borderPane.setCenter(scrollPane);
    }

    private void returnBookClicked(Book book) {
        Member user = UserState.getInstance().getLoggedInUser();
        readingRoomService.returnBook(book, user);
        displayBooks();;
    }

    @Override
    protected Node getStage() {
        return null;
    }
}
