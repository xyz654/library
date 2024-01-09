package library.lib.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import library.lib.backend.models.Author;
import library.lib.backend.models.Book;
import library.lib.backend.models.Statistics;
import library.lib.backend.services.AuthorService;
import library.lib.backend.services.BookService;
import library.lib.backend.services.ReadingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class StatisticsController extends BaseController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private BarChart<String, Number> barChartAuthor;

    @FXML
    private BarChart<String, Number> barChartCategory;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private CategoryAxis xAxisAuthor;

    @FXML
    private CategoryAxis xAxisCategory;

    @FXML
    private Button goBack;

    @Autowired
    ReadingRoomService readingRoomService;

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;

    @Override
    protected Node getStage() {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayRentedBooksStats();
        displayAuthorBookCountStats();
        displayCategoryStats();
    }

    private ObservableList<XYChart.Series<String, Number>> createBookCountChartData(List<Statistics> stats, Set<String> uniqueCategories) {
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();
        Map<String, Integer> monthBookCountMap = new HashMap<>();

        for (Statistics stat : stats) {
            String monthYear = String.format("%d-%02d", stat.getYear(), stat.getMonth());
            monthBookCountMap.put(monthYear, monthBookCountMap.getOrDefault(monthYear, 0) + stat.getCount());
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String monthYear : uniqueCategories) {
            int bookCount = monthBookCountMap.getOrDefault(monthYear, 0);
            series.getData().add(new XYChart.Data<>(monthYear, bookCount));
        }

        barChartData.add(series);
        return barChartData;
    }

    private void displayRentedBooksStats() {
        List<Statistics> stats = readingRoomService.getStats();

        Set<String> uniqueCategories = new HashSet<>(stats.stream()
                .map(stat -> String.format("%d-%02d", stat.getYear(), stat.getMonth()))
                .collect(Collectors.toList()));

        xAxis.setCategories(FXCollections.observableArrayList(new ArrayList<>(uniqueCategories)));

        ObservableList<XYChart.Series<String, Number>> barChartData = createBookCountChartData(stats, uniqueCategories);
        barChart.setData(barChartData);
    }

    private ObservableList<XYChart.Series<String, Number>> createAuthorBookCountChartData(List<Author> authors) {
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Author author : authors) {
            List<Book> booksByAuthor = bookService.getBooksByAuthor(author);
            series.getData().add(new XYChart.Data<>(author.getName(), booksByAuthor.size()));
        }

        barChartData.add(series);
        return barChartData;
    }

    private void displayAuthorBookCountStats() {
        List<Author> authors = authorService.getAllAuthors();
        xAxisAuthor.setCategories(FXCollections.observableArrayList(authors.stream().map(Author::getName).collect(Collectors.toList())));

        ObservableList<XYChart.Series<String, Number>> barChartData = createAuthorBookCountChartData(authors);
        barChartAuthor.setData(barChartData);
    }

    private ObservableList<XYChart.Series<String, Number>> createCategoryChartData(List<Book> books) {
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

        Map<String, Integer> categoryCountMap = books.stream()
                .collect(Collectors.groupingBy(book -> book.getCategory().getName(), Collectors.summingInt(book -> 1)));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChartData.add(series);
        return barChartData;
    }

    private void displayCategoryStats() {
        List<Book> books = bookService.getAllBooks();
        xAxisCategory.setCategories(FXCollections.observableArrayList(
                books.stream().map(book -> book.getCategory().getName()).distinct().collect(Collectors.toList())));

        ObservableList<XYChart.Series<String, Number>> barChartData = createCategoryChartData(books);
        barChartCategory.setData(barChartData);
    }

    @FXML
    private void redirectToAllBooks() {
        redirectToScene("/library/lib/book-list-view.fxml", "Books", (Stage) goBack.getScene().getWindow());
    }
}
