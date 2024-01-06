package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import library.lib.backend.models.Book;
import library.lib.backend.models.ReadingRoom;
import library.lib.frontend.state.SpringContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class RentedBookController {
    ReadingRoom readingRoom;

    @FXML
    private VBox bookContainer;

    @FXML
    private ImageView image;

    @FXML
    private Text author;

    @FXML
    private Text bookName;

    @FXML
    private Text category;

    @FXML
    private Text date;
    public void setReadingRoom(ReadingRoom readingRoom) {
        this.image.setImage(new Image(readingRoom.getBook().getBookCover()));
        this.bookName.setText(readingRoom.getBook().getTitle());
        this.author.setText(readingRoom.getBook().getAuthor().getName());
        this.category.setText(readingRoom.getBook().getCategory().getName());
        this.date.setText(
                readingRoom.getStart_date().toString() + " - " + readingRoom.getEnd_date().toString()
        );
        this.readingRoom = readingRoom;
    }
}
