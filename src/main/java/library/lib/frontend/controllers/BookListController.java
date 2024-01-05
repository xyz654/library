package library.lib.frontend.controllers;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import library.lib.backend.models.Author;
import library.lib.backend.models.Book;
import library.lib.backend.models.Category;
import library.lib.backend.models.Filters;
import library.lib.backend.services.AuthorService;
import library.lib.backend.services.BookService;
import library.lib.frontend.state.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class BookListController extends BaseController {

    @FXML
    private ListView<Book> bookListView;

    @FXML
    private TextField searchBar;

    @FXML
    private VBox searchButton;

    @FXML
    private ComboBox<String> authorSelect;

    @FXML
    private ComboBox<String> categorySelect;

    @FXML
    private ComboBox<Boolean> isLoanedBooksVisible;

    @FXML
    private Button clearButton;

    @FXML
    private Button filterButton;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    private List<Book> books;

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
        setupFilters();
        initializeBooks();
    }

    private void initializeBooks() {
        List<Book> books = bookService.getAllBooks();
        this.books = books;
        displayBooks(books);
        setupListView();
    }

    private void setupFilters() {
        authorSelect.setPromptText("Select Author");
        categorySelect.setPromptText("Select Category");
        isLoanedBooksVisible.setPromptText("Show loaned books?");

        List<Author> authors = authorService.getAllAuthors();
        ObservableList<String> authorNames = FXCollections.observableArrayList();
        for (Author author : authors) {
            authorNames.add(author.getName());
        }
        authorSelect.setItems(authorNames);

        List<Category> categories = bookService.getAllCategories();
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        categorySelect.setItems(categoryNames);

        ObservableList<Boolean> booleans = FXCollections.observableArrayList(Arrays.asList(false, true));
        isLoanedBooksVisible.setItems(booleans);
    }

    private void displayBooks(List<Book> books) {
        ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);
        bookListView.setItems(observableBooks);
    }

    private void setupListView() {
        bookListView.setStyle("-fx-background-color: transparent;");
        bookListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bookListView.setCellFactory(createListCellFactory());
    }

    @FXML
    private void handleClear() {
        authorSelect.setValue(null);
        categorySelect.setValue(null);
        isLoanedBooksVisible.setValue(null);
        searchBar.setText("");

        initializeBooks();
    }

    @FXML
    private void handleFilter() {
        String authorName = authorSelect.getValue();
        Author author = authorName == null ? null : authorService.getAuthorByName(authorName).get(0);

        String categoryName = categorySelect.getValue();
        Category category = categoryName == null ? null : bookService.getCategoryByName(categoryName);

        boolean showLoaned = isLoanedBooksVisible.getValue() != null;

        Filters filters = new Filters(category, author, null, showLoaned);
        List<Book> books = bookService.getBooksWithCustomQuery(filters);
        this.books = books;
        displayBooks(books);
        setupListView();
    }

    @FXML
    private void handleSearch() {
        String searchedTitle = searchBar.getText();
        if (searchedTitle != null && !searchedTitle.equals("")) {
            List<Book> filteredBooks =
                    this.books.stream()
                            .filter(book -> book.getTitle() != null && book.getTitle().toLowerCase().contains(searchedTitle.toLowerCase()))
                            .toList();
            displayBooks(filteredBooks);
            setupListView();
        } else {
            displayBooks(this.books);
            setupListView();
        }
    }

    private Callback<ListView<Book>, ListCell<Book>> createListCellFactory() {
        return param -> new CustomListCell();
    }

    private class CustomListCell extends ListCell<Book> {
        @Override
        protected void updateItem(Book item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                setGraphic(createBookCell(item, getIndex()));
                setOnMouseClicked(event -> openBookDetails(item));
            } else {
                setGraphic(null);
            }
        }
    }

    private GridPane createBookCell(Book item, int index) {
        GridPane gridPane = new GridPane();

        ImageView imageView = new ImageView(new Image(item.getBookCover()));
        imageView.setFitWidth(IMAGE_FIT_WIDTH);
        imageView.setPreserveRatio(true);
        gridPane.add(imageView, 0, 0);

        Text text = new Text(
                "Title: " + item.getTitle() +
                        "\nAuthor: " + item.getAuthor().getName() +
                        "\nCategory: " + (item.getCategory() != null ? item.getCategory() : UNDEFINED_CATEGORY)
        );
        text.setStyle("-fx-font-size: " + TEXT_FONT_SIZE + "px; -fx-fill: " + TEXT_FILL_COLOR + ";");
        GridPane.setMargin(text, TEXT_MARGIN);
        gridPane.add(text, 1, 0);

        String backgroundColor = (index % 2 == 0) ? ODD_BACKGROUND_COLOR : EVEN_BACKGROUND_COLOR;
        gridPane.setStyle("-fx-padding: " + PANE_PADDING + "; -fx-background-color: " + backgroundColor + ";");

        return gridPane;
    }

    private void openBookDetails(Book selectedBook) {
        try {
            ConfigurableApplicationContext springContext = SpringContext.getInstance().getContext();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/book-details-view.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            BookDetailsController detailsController = loader.getController();
            detailsController.setBookDetails(selectedBook);
            Scene scene = bookListView.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Node getStage() {
        return null;
    }
}
