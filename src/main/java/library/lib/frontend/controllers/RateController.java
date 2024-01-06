package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.*;
import library.lib.backend.services.RateService;
import library.lib.frontend.state.SpringContext;
import library.lib.frontend.state.UserState;
import org.controlsfx.control.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateController extends BaseController{
    Book book;
    @Autowired
    RateService rateService;
    @FXML
    Button addRate;
    @FXML
    Button goToBook;
    @FXML
    Text bookName;
    @FXML
    Rating ratingStars;
    @FXML
    TextArea descriptionArea;
    @FXML
    Text msg;



    public void setBook(Book book) {
        this.book = book;
        updateView();
    }


    @FXML
    void addRateClick(){
        String description = descriptionArea.getText();
        int starRate = (int)ratingStars.getRating();
        Member user = UserState.getInstance().getLoggedInUser();
        Rate rate = new Rate(user, this.book, starRate, description);
        ReturnModel rateModel = rateService.addReview(rate);
        if (rateModel.code == ReturnCodes.OK) {
            msg.setText("Thanks, rate added!");
        }
    }


    @FXML
    void goToBook(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/book-details-view.fxml"));
            Parent root = loader.load();

            BookDetailsController detailsController = loader.getController();
            detailsController.setBookDetails(this.book);

            Stage stage = (Stage) goToBook.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateView(){
        bookName.setText("Rate "+this.book.getTitle());
    }
    @Override
    protected Node getStage() {
        return null;
    }



}
