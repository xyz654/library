package library.lib.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.*;
import library.lib.backend.services.BookService;
import library.lib.backend.services.MemberService;
import library.lib.backend.services.RateService;
import library.lib.backend.services.ReadingRoomService;
import library.lib.frontend.state.SpringContext;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
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

    @FXML
    private Button rentBook;
    @FXML
    private Button reserveBook;
    @FXML
    private Button rate;
    @FXML
    private Text awaitingMembersLabel;
    @FXML
    ListView rates;

    @Autowired
    ReadingRoomService readingRoomService;

    @Autowired
    BookService bookService;

    @Autowired
    MemberService memberService;

    @Autowired
    RateService rateService;

    private Book bookDetails;

    public void setBookDetails(Book book) {
        this.bookDetails = book;
        updateView();
        updateReserveButtonText(bookDetails);
        updateRentButtonText(bookDetails);
        showRateButton();
        showRates();
    }

    private void updateView() {
        if (bookDetails != null) {
            titleLabel.setText(bookDetails.getTitle());
            authorLabel.setText(bookDetails.getAuthor().getName());
            descriptionLabel.setText(bookDetails.getDescription());
            categoryLabel.setText("Category: " + (bookDetails.getCategory() != null ? bookDetails.getCategory() : "Undefined"));
            String awaitingLabel = "Awaiting users: " + bookDetails.getAwaitingMembers().size();
            List<Member> awaitingMembers = bookDetails.getAwaitingMembers();
            if(awaitingMembers.size()> 0){
                for(Member member : awaitingMembers){
                    if(member.getId() == UserState.getInstance().getLoggedInUser().getId()){
                        awaitingLabel += "(Including you) " ;
                    }
                }
            }
            awaitingMembersLabel.setText(awaitingLabel);
            coverImageView.setImage(new Image(bookDetails.getBookCover()));
            updateRentButtonText(bookDetails);
            showRateButton();
        }
    }

    @FXML
    private void rentBook() {
        if (bookDetails.getLoaner() != null) return;
        Member user = UserState.getInstance().getLoggedInUser();
        ReturnModel returnModel = readingRoomService.rentBook(bookDetails, user);
        if (returnModel.code == ReturnCodes.OK) {
            bookDetails = bookService.getBookById(bookDetails.getId());
            Optional<Member> updatedMember = memberService.getMember(user);
            updatedMember.ifPresent(member -> UserState.getInstance().setLoggedInUser(member));
            updateRentButtonText(bookDetails);
        }
    }
    @FXML
    private void reserveBook(){
        Member user = UserState.getInstance().getLoggedInUser();
        ReturnModel returnModel = readingRoomService.reserveBook(bookDetails, user);
        if (returnModel.code == ReturnCodes.OK) {
            bookDetails = bookService.getBookById(bookDetails.getId());
            Optional<Member> updatedMember = memberService.getMember(user);
            updatedMember.ifPresent(member -> UserState.getInstance().setLoggedInUser(member));
            updateReserveButtonText(bookDetails);
        }
    }

    public void goBackToBookList() {
        redirectToScene("/library/lib/book-list-view.fxml", "Books", (Stage) goBack.getScene().getWindow());
    }

    private void updateRentButtonText(Book bookDetails) {
        if (bookDetails.getLoaner() == null) {
            rentBook.setText("Rent Book");
            rentBook.setStyle("-fx-background-color: #B97A57; -fx-text-fill: #FFFFFF");
            rentBook.setOpacity(1);
            reserveBook.setOpacity(0);
        } else {
            rentBook.setText("Book is already rented");
            rentBook.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF");
            rentBook.setOpacity(0);
            reserveBook.setOpacity(1);
        }
    }

    private void updateReserveButtonText(Book bookDetails) {
        if (bookDetails.getLoaner() == null) {
            reserveBook.setText("Reserve Book");
            reserveBook.setStyle("-fx-background-color: #B97A57; -fx-text-fill: #FFFFFF");
            reserveBook.setOpacity(0);
        } else {
            for(Member member : bookDetails.getAwaitingMembers()){
                if(member.getId() == UserState.getInstance().getLoggedInUser().getId()){
                    reserveBook.setText("Book is already reserved by you");
                    reserveBook.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF");
                    return;
                }
            }
            if(bookDetails.getLoaner().getId() == UserState.getInstance().getLoggedInUser().getId()){
                reserveBook.setText("Book is already rented by you");
            }
            else{
                reserveBook.setText("Reserve Book");
            }
            reserveBook.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF");
            rentBook.setOpacity(1);

        }
    }
    public void rateBook(){

        try {
            ConfigurableApplicationContext springContext = SpringContext.getInstance().getContext();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/rate-view.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            RateController rateController = loader.getController();
            rateController.setBook(this.bookDetails);

            Stage stage = (Stage) rate.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRates(){
        List<Rate> allRates = rateService.getReviewsByBookId(bookDetails.getId());
        ObservableList<String> ratesDisplay = FXCollections.observableArrayList(
                allRates.stream()
                        .map(Rate::getDisplayFormat)
                        .collect(Collectors.toList())
        );
        rates.setItems(ratesDisplay);

    }

    private void showRateButton(){
        Member user = UserState.getInstance().getLoggedInUser();
        List<Integer> allIds = readingRoomService.getRentedBooksByMember(user).stream().map(Book::getId).collect(Collectors.toList());
        if(allIds.contains(bookDetails.getId())) rate.setOpacity(1);
        else rate.setOpacity(0);
    }

    @Override
    protected javafx.scene.Node getStage() {
        return null;
    }
}
