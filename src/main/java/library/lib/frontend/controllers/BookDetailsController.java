package library.lib.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.ReturnCodes;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.services.BookService;
import library.lib.backend.services.MemberService;
import library.lib.backend.services.ReadingRoomService;
import library.lib.frontend.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    private Text awaitingMembersLabel;

    @Autowired
    ReadingRoomService readingRoomService;

    @Autowired
    BookService bookService;

    @Autowired
    MemberService memberService;

    private Book bookDetails;

    public void setBookDetails(Book book) {
        this.bookDetails = book;
        updateView();
        updateReserveButtonText(bookDetails);
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
            reserveBook.setOpacity(0);
        } else {
            rentBook.setText("Book is already rented");
            rentBook.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF");
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

    @Override
    protected javafx.scene.Node getStage() {
        return null;
    }
}
