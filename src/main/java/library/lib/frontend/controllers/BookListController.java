package library.lib.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import library.lib.backend.models.Author;
import library.lib.backend.models.Book;
import library.lib.backend.models.Category;
import library.lib.backend.models.Filters;
import library.lib.backend.services.AuthorService;
import library.lib.backend.services.BookService;
import library.lib.frontend.layout.BootstrapColumn;
import library.lib.frontend.layout.BootstrapPane;
import library.lib.frontend.layout.BootstrapRow;
import library.lib.frontend.layout.Breakpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class BookListController extends BaseController {
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
    private BorderPane borderPane;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    private List<Book> books;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFilters();
        initializeBooks();
    }

    private void initializeBooks() {
        List<Book> books = bookService.getAllBooks();
        this.books = books;
        displayBooks(books);
    }

    private void setupFilters() {
        authorSelect.setPromptText("All");
        categorySelect.setPromptText("All");
        isLoanedBooksVisible.setPromptText("true");

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
        BootstrapPane bootstrapPane = new BootstrapPane();
        bootstrapPane.setPadding(new Insets(15));
        bootstrapPane.setVgap(25);
        bootstrapPane.setHgap(25);

        BootstrapRow row = new BootstrapRow();
        for (Book book: books) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/lib/book.fxml"));
                VBox bookBox = loader.load();
                BookController bookController = loader.getController();
                bookController.setBook(book);
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

    @FXML
    private void handleClear() {
        authorSelect.setValue(null);
        authorSelect.setPromptText("Select author");
        authorSelect.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("Select author");
                } else {
                    setText(item);
                }
            }
        });
        categorySelect.setPromptText("Select category");
        categorySelect.setValue(null);
        categorySelect.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("Select category");
                } else {
                    setText(item);
                }
            }
        });
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

        boolean showLoaned = isLoanedBooksVisible.getValue() != null ? isLoanedBooksVisible.getValue() : true;

        Filters filters = new Filters(category, author, null, !showLoaned);
        List<Book> books = bookService.getBooksWithCustomQuery(filters);
        this.books = books;
        displayBooks(books);
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
        } else {
            displayBooks(this.books);
        }
    }

    @Override
    protected Node getStage() {
        return null;
    }
}
