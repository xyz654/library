package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Book;

public class BookDetailsController extends BaseController {

    @FXML
    private Text titleLabel;

    @FXML
    private Text authorLabel;

    @FXML
    private Text descriptionLabel;

    @FXML
    private Text categoryLabel;

    @FXML
    private ImageView coverImageView;

    @FXML
    private Button goBack;

    private Book bookDetails;

    public void setBookDetails(Book book) {
        this.bookDetails = book;
        updateView();
    }

    private void updateView() {
        if (bookDetails != null) {
            titleLabel.setText(bookDetails.getTitle());
            authorLabel.setText(bookDetails.getAuthor().getName());
            descriptionLabel.setText(bookDetails.getDescription());
            categoryLabel.setText("Category: " + (bookDetails.getCategory() != null ? bookDetails.getCategory() : "Undefined"));
            coverImageView.setImage(new Image(bookDetails.getBookCover()));
        }
    }

    public void goBackToBookList() {
        redirectToScene("/library/lib/book-list-view.fxml", "Books", (Stage) goBack.getScene().getWindow());
    }

    @Override
    protected javafx.scene.Node getStage() {
        return null;
    }
}
