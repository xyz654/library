# Projekt czytelnia

### Autorzy

Marcin Bereta, Hubert Gancarczyk, Magdalena Skrok, Laura Wiktor

# Opis

Projekt zakłada stworzenie aplikacji obsługującej działalność czytelni, oferującej trzy poziomy dostępu: użytkownika, pracownika i administratora.

Użytkownik ma możliwość rejestracji w systemie, podając swój adres email, nick oraz ustawiając hasło zgodne z regułami. Dzięki temu będzie mógł zalogować się do systemu, wyświetlić polecane książki, ocenić te które chociaż raz czytał, wyświetlić statystyki, sprawdzić spis treści i okładkę wybranej pozycji, dodatkowo będzie mógł wyświetlić książki danego autora.

Funkcje pracownika obejmują przyjmowanie zwracanych książek oraz udzielanie kolejnych wypożyczeń czytelnikom. Natomiast uprawnienia administratora pozwalają na dodawanie nowych książek do bazy danych. Oboje mają możliwość podglądu aktualnego stanu czytelni.

Cała aplikacja jest oparta na języku Java oraz wykorzystuje framework Spring Boot. Struktura projektu została logicznie podzielona na frontend i backend. Backend odpowiada za połączenie z bazą danych oraz obsługę systemu, natomiast frontend zajmuje się warstwą wizualną, zapewniając interakcję użytkownika z aplikacją.

Aby uruchomić projekt należy uruchomić LibraryApp.

# Schemat bazy danych

![Alt text](image-6.png)

Baza danych zawiera pięć tabel obsługujących funkcjonalność aplikacji. Tabela member przechowuje informacje o pracowniku, administratorze lub czytelniku. Tabela Book odpowiada za dane dotyczące pojedynczego egzemplarza książek będących na stanie czytelni. Reading_room przechowuje dane o korzystających z czytelni, a Rate pozwala na ocenę danej książki przez czytelnika. Tabela Author przechowuje informacje o autorach dostępnych pozycji.

# Aktualny - niekompletny schemat aplikacji z zależnościami Spring'a

![Alt text](image-8.png)

# Aktualny - niekompletny schemat obiektowy aplikacji

![Alt text](image-9.png)

# Zakładany końcowy model obiektowy

## Widoki

**Widok użytkownika** - będzie udostępniał najlepsze statystyki, możliwość oceny czytanych książek. Aktualnie udostępnia ich listę. Z widoku użytkownika można przejść do widoku konkretnej książki. \
**Widok pracownika** - będzie umożliwiał rozpoczęcie wizyty czytelnika jak i jej zakończenie, ponadto będzie dostarczał możliwość aktualnego podglądu czytelni - użytkowników i posiadanych przez nich książek. \
**Widok administratora** - umożliwia dodanie nowej książki do czytelni oraz nowego autora. Ma możliwość zmiany funkcji każdego użytkownika. \
**Widok książek** - pozwala przeglądać dostępne pozycje, będzie umożliwiał wyświetlenie wszystkich książek podanego autora lub wybranej kategorii. \
**Widok dodawania książki** - pozwala dodać nową książkę do czytelni. \
**Widok dodawania autora** - pozwala dodać nowego autora do czytelni. \
**Widok dodawania kategorii** - pozwala dodać nową kategorię do czytelni. \ 
**Widok administratora** - umożliwia dodanie nowej książki do czytelni oraz nowego autora. Ma możliwość zmiany funkcji każdego użytkownika. \
**Widok obecnie wypożyczonych książek** - pozwala wyświetlić wszystkie wypożyczone książki przez użytkownika. \
**Widok listy użytkowników** - pozwala wyświetlić wszystkich użytkowników. \
**Widok oceniania książki** - pozwala ocenić książkę. \
**Widok wypożyczonych książek** - pozwala wyświetlić wszystkie wypożyczone książki aktualnie. \
**Widok wszystkich wypożyczeń ** - pozwala wyświetlić wszystkie wypożyczenia. \
**Widok statystyk** - pozwala wyświetlić statystyki. \
**Widok profilu użytkownika** - pozwala wyświetlić profil użytkownika i nim zarządzać. \

### Już zaimplementowane:

**BaseController** - abstrakcyjna klasa kontrolera interfejsu użytkownika. Zawiera metody obsługujące nawigację między scenami oraz obsługę logowania.\
**Metody:**\
void redirectToScene(String scenePath, String title, Stage stage)\
void handleSuccessfulLogin(Member member)\
Node getStage()\
void initialize(URL url, ResourceBundle resourceBundle)

**LoginController** - kontroler obsługujący widok logowania dostępny po uruchomieniu aplikacji i przejściu przez odnośnik dotyczący posiadania już konta. W przypadku poprawnych danych loguje użytkownika i przenosi go do nowego widoku, w przeciwnym przypadku wyświetla stosowny komunikat.\
**Metody:**\
void onKeyPressed()\
void redirectToSignUp()\
void onLoginClick()\
void hideErrorMessage()\
void showErrorMessage(String message)\
void handleSuccessfulLogin(Member loggedInUser)\
Node getStage()

**RegisterController** - kontroler obsługujący widok rejestracji dostępny po uruchomieniu aplikacji. W przypadku poprawnych danych rejestruje nowego użytkownika i przenosi go do nowego widoku, w przeciwnym przypadku wyświetla stosowny komunikat. Posiada też opcję przejącia do widoku logowania dla zarejestrowanych użytkowników. \
**Metody:**\
void onKeyPressed()\
void redirectToLogin()\
void onRegisterClick()\
void hideErrorMessage()\
void showErrorMessage(String message)\
void handleSuccessfulLogin(Member loggedInUser)\
Node getStage()

**AddNewAuthorController** - klasa dziedzicząca po BaseController służąca do dodawania nowego autora. \
**Metody:**\
void goToAdminPanel()\
void addAuthorClick()\
Node getStage()

**AddNewCategoryController** - klasa dziedzicząca po BaseController służąca do dodawania nowej kategorii. \
**Metody:**\
void goToAdminPanel()\
void addCategoryClick()\
Node getStage()

**AddNewBookController** - klasa dziedzicząca po BaseController służąca do dodawania nowej książki dla istniejących już w bazie autorów. \
**Metody:**\
void goToAdminPanel()\
void addBookClick()\
void initialize(URL url, ResourceBundle resourceBundle)\
Node getStage()

**AdminPanelController** - klasa dziedzicząca po BaseController służąca do obsługi wyświetlenia możliwości akcji dostępnych dla administratora. \
**Metody:**\
void redirectToMemberList()\
void redirectToAddBook()\
void redirectToAddAuthor()\
void redirectToDashboard()\
Node getStage()

**BookDetailsController** - klasa dziedzicząca po BaseController wyświetlenia szczegółowych informacji odnośnie książki i jej wypożyczenia.\
**Metody:**\
void setBookDetails(Book book)\
void updateView()\
void rentBook()\
void reserveBook()\
void goBackToBookList()\
void updateReserveButtonText(Book bookDetails)\
void updateRentButtonText(Book bookDetails)\
void rateBook()\
void showRates()\
void showRateButton()\
Node getStage()

**BookListController** - klasa dziedzicząca po BaseController wyświetlenia wszystkie książki w bibliotece.\
**Metody:**\
void initialize(URL url, ResourceBundle resourceBundle)\
void displayBooks()\
void setupListView()\
Callback<ListView<Book>, ListCell<Book>> createListCellFactory()\
Node getStage()

**CurrentRentedBooksController** - klasa dziedzicząca po BaseController wyświetlenia wszystkie książki obecnie wypożyczone przez użytkownika, umożliwia ich zwrot.\
**Metody:**\
void initialize(URL url, ResourceBundle resourceBundle)\
void displayBooks()\
void setupListView()\
Callback<ListView<Book>, ListCell<Book>> createListCellFactory()\
Node getStage()

**DashboardController** - klasa dziedzicząca po BaseController służąca do obsługi podstawowego widoku po zalogowaniu użytkownika - prototyp widoku użytkownika. Pobiera i wyświetla ostationo zalogowanych użytkowników.\
**Metody:**\
void initialize(URL url, ResourceBundle resourceBundle)\
void redirectToBooksList()\
void redirectToCurrentRentedBooks()\
void redirectToRentedBooksHistory()\
Node getStage()

**MemberListController** - klasa dziedzicząca po BaseController wyświetlenia wszystkich użytkowników biblioteki.\
**Metody:**\
void initialize(URL url, ResourceBundle resourceBundle)\
void displayMembers()\
void setupListView()\
Callback<ListView<Book>, ListCell<Book>> createListCellFactory()\
Node getStage()

**NavbarController** - klasa dziedzicząca po BaseController wyświetla navbar.\
**Metody:**\
void initialize(URL url, ResourceBundle resourceBundle)\
void redirectToBooksList()\
void redirectToCurrentRentedBooks()\
void redirectToRentedBooksHistory()\
void redirectToDashboard()\
void redirectToAdminPanel()\
Node getStage()

**RentedBooksHistoryController** - klasa dziedzicząca po BaseController wyświetlenia historię wypożyczeń książek.\
**Metody:**\
void initialize(URL url, ResourceBundle resourceBundle)\
void displayBooks()\
void setupListView()\
Callback<ListView<Book>, ListCell<Book>> createListCellFactory()\
Node getStage()

**RateController** - klasa dziedzicząca po BaseController służy do oceninia książki\
**Metody:**\
void setBook(Book book)\
void addRateClick()\
void goToBook()\
void updateView()\
Node getStage()

**RentedBookController** - klasa służąca do wyświetlania książki wypożyczonej\
**Metody:**\
void setReadingRoom(ReadingRoom readingRoom)

**RentedBooksHistoryController** - klasa dziedzicząca po BaseController służy do wyświetlania wszystkich wypożyczonych książek\
**Metody:**\
void initialize(URL url, ResourceBundle resourceBundle)\
void initializeReadingRooms()\
void setupFilters()\
void displayReadingRooms(List<ReadingRoom> readingRooms)\
void handleClear()\
void handleFilter()\
void handleSearch()\
Node getStage()

## Serwisy

**StatisticsService** - obsłuży logikę biznesową statystyk - dodanie opinii i najlepsze opinie bez lub wraz z opisem, będzie posiadał metody:
_getBestBooks_, _getBestOpinions_, _addRate_. \

### Już zaimplementowane:

**MemberService** - obsłuży logikę biznesową związaną z obsługą czytelnika - logowanie, rejestracja oraz pobranie listy najnowszych członków. \
**Konstruktor:** MemberService(MemberRepository memberRepository) \
**Metody:**\
ReturnModel register(String username, String password, String email)\
ReturnModel login(String email, String password)\
List\<Member> getLatestMembers(int memberCount)

**BookService** - obsłuży logikę biznesową książek - książki danego autora, danej kategorii lub o danym tytule oraz dodanie nowej książki.\
**Konstruktor:** BookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) \
**Metody:**\
ReturnModel addBook(Book book, Member member)\
List<Book> getFreeBooks()\
Book getBookById(int id)\
ReturnModel addCategory(String name)\
List<Book> getAllBooks()\
List<Book> getBooksByTitle(String title)\
List<Book> getBooksByAuthor(Author author)\
List<Book> getBooksByCategory(String category)\
List<Book> getBooksWithCustomQuery(Filters filters)

**ReadingRoomService** - obsłuży logikę biznesową czytelni - początek i koniec wizyty oraz aktualnie trwające wizyty jak i aktualną wizytę konkretnego użytkownika.
**Konstruktor:** ReadingRoomService(ReadingRoomRepository readingRoomRepository, MemberRepository memberRepository, BookRepository bookRepository) \
**Metody:**\
ReturnModel rentBook(Book book, Member member)\
List<Book> getRentedBooksByMember\
void returnBook(Book book, Member member)\
List<Book> getAllRentedBooks()\
List<Book> getAllReturnedBooks()\
List<ReadingRoom> getUserHistory(Member member)\
List<ReadingRoom> getCurrentRentedBooksByMember(Member member)
List<ReadingRoom> getUserHistoryWithFilters(Filters filters)\
List<Statistics> getStats()\
ReturnModel reserveBook(Book book, Member member)

**AuthorService** - obsłuży logikę authora - książki danego autora, danej kategorii lub o danym tytule oraz dodanie nowej książki.\
**Konstruktor:** AuthorService( AuthorRepository authorRepository) \
**Metody:**\
List<Author> getAllAuthors()\
List<Author> getAuthorByName(String authorName)\
ReturnModel addAuthor(Author author)

**EmailSenderService** - obsłuży logikę wysyłania maili .\
**Metody:**\
void sendEmail(String to, String subject, String text)\
void sendBookReminderMail(Book book)\
void sendRentConfirmationMail(Book book)\
void sendUpInQueueMail(Book book)

**RateService** - obsłuży logikę oceniania książki. \
**Konstruktor:** RateService(RatesRepository ratesRepository, MemberRepository memberRepository) \
**Metody:**\
ReturnModel addReview(Rate rate)\
List<Rate> getReviewsByBookId(Integer bookId)\
List<Statistics> getBookStatistics()

## Persystencje

**StatisticsRepository** - rozszerzy interfejs JpaRepository o: _getBestBooks_ (pobierze z bazy najlepsze książki porównując punkty), _getBestOpinions_ (pobierze najlepsze książki wraz z ich opisowymi opiniami). \

### Już zaimplementowane:

**MemberRepository** - rozszerza domyślny interfejs JpaRepository o metodę _findByEmail_ zwracającą obiekt Member na podstawie podanego adresu email, o ile istnieje.
**AuthorRepository** - rozszerza domyślny interfejs JpaRepository o metodę findByName, zwracająca authora jeżeli taki istnieje.\
**BookRepository** - rozszerza domyślny interfejs JpaRepository o metody:\
List<Book> findByTitle(String title)\
List<Book> findByAuthor(Author author)\
List<Book> findByCategory(String category)\
List<Book> findByNullMember()

**ReadingRoom** - rozszerza domyślny interfejs JpaRepository o metody:\
List<Book> getRentedBooksByUser(Member member)\
List<ReadingRoom> getCurrentReadingBooksByUser(Member member)\
List<ReadingRoom> getAllRentedBooksByUser(Member member)\
List<Book> getCurrentRentedBooks()\
List<Book> getPreviouslyRentedBooks()\
List<ReadingRoom> getReadingRoom(Book book, Member member)

## Modele

**Statistics** - reprezentujący konkretną opinię - tabelę Rate z bazy, poza podstawowymi metodami będzie posiadał metodę _checkRate_, która sprawdzi czy wartość wystawionej opinii znajduje się w przedziale 0-5.

### Już zaimplementowane:

**Member** - reprezentujący członka czytelni - tabelę Member z bazy, poza podstawowymi metodami posiada metody _verifyEmail_ i _verifyPassword_, które sprawdzają poprawność danych wejściowych. \
**Konstruktor:** Member(String name, String email, String password)

**Book** - reprezentujący książkę - tabelę Book z bazy, posiada podstawowe metody. \
**Konstruktor:** Book(String title, String author, String description, String bookCover, String category)

**Author** - reprezentujący autora - tabelę Author z bazy, posiada podstawowe metody addBook, która pozwala dodać do niego napisaną przez niego książkę. \
**Konstruktor:** Author(String name, String description, String nick)

**ReadingRoom** - reprezentujący instancję wypożyczenia - tabelę ReadingRoom z bazy, posiada podstawowe metody. \
**Konstruktor:** ReadingRoom(Member member, Book book, Date start_date)

## Pozostałe zaimplementowane klasy potrzebne do działania aplikacji:

### MemberConfigurator

Jest to klasa konfiguracyjna, która w przypadku braku jakiegokolwiek członka biblioteki doda do niej przykładowego użytkownika.

### Permissions

Enum zawierający dostępne funkcje w aplikacji – admin, pracownik, członek biblioteki.

### ReturnModel

Klasa reprezentuje model zwracany przez aplikację, zawiera on obiekt, wiadomość i kod z nim związany. \
**Konstruktor:** ReturnModel(ReturnObject object, String message, int code)

### ReturnObject

Interfejs posiadający jedną metodę _toJson_ konwertującą obiekty implementuje ten interfejs do formatu JSON.

### Filters
Klasa służacą do przekazywania filtrów do bazy danych. \
**Konstruktor:** Filters(Category category, Author author, String title, boolean free)\
Filters(Category category, Author author, String title, boolean free, Member member)

### Statistics
Klasa służacą do przekazywania statystyk do frontendu. \
**Konstruktor**
Statistics(Double average, Integer count, Book book)\
Statistics(Integer count, Integer day, Integer month, Integer year)

### SpringContext

Singleton przechowujący kontest aplikacji Spring. \
**Konstruktor:** SpringContext() \
**Metody:** \
SpringContext getInstance() \
ConfigurableApplicationContext getContext() \
void setContext(ConfigurableApplicationContext context)

### UserState

Singleton przechowujący informacje o zalogowanym użytkowniku. \
**Konstruktor:** UserState() \
**Metody:** \
UserState getInstance() \
Member getLoggedInUser() \
void setLoggedInUser(Member loggedInUser)

### Validator

Klasa obsługuje sprawdzanie poprawności podawanych przez użytkownika danych. \
**Konstruktor:** Service() \
**Metody:** \
 boolean validateEmail(String email)\
 boolean validateUsername(String username)\
 boolean validatePassword(String password)

### LibraryApp

Klasa obsługuje frontend aplikacji - interfejs użytkownika. \
 **Metody:** \
void start(Stage primaryStage) \
void configureStage(Stage primaryStage) \
void init()\
void main(String[] args)

# Aktualny wygląd aplikacji

### Widok rejestracji

![Alt text](image-1.png)

### Widok logowania

![Alt text](image-2.png)

### Widok po zalogowaniu

![Alt text](image-5.png)

### Widok listy książek

![Alt text](image-10.png)

### Widok książki z szczegółami

![Alt text](image-11.png)

### Widok wypożyczonych książek

![Alt text](image-12.png)

### Widok historii wypożyczeń

![Alt text](image-13.png)

### Widok panelu administratora

![Alt text](image-17.png)

### Widok zarządzania użytkownikami

![Alt text](image-14.png)

### Widok formularza dodawania autora

![Alt text](image-16.png)

### Widok formularza dodawania książki

![Alt text](image-15.png)

### Widok statystyk

![Alt text](image-18.png)

### Widok panelu użytkownika

![Alt text](image-19.png)

### Widok listy książek z filtrami

![Alt text](image-20.png)

### Widok oceniania książki

![Alt text](image-21.png)

### Widok powiadaminia mailem

![Alt text](image-22.png)

### Widok formularza dodawania kategorii

![Alt text](image-23.png)