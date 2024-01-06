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
import library.lib.frontend.state.SpringContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class BookController {
    Book book;

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
    public void setBook(Book book) {
        this.image.setImage(new Image(book.getBookCover()));
        this.bookName.setText(book.getTitle());
        this.author.setText(book.getAuthor().getName());
        this.category.setText(book.getCategory().getName());
        this.book = book;
    }

    @FXML
    private void openBookDetails() {
        try {
            ConfigurableApplicationContext springContext = SpringContext.getInstance().getContext();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/book-details-view.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            BookDetailsController detailsController = loader.getController();
            detailsController.setBookDetails(this.book);
            Scene scene = bookContainer.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
