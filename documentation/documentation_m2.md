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
**Widok książki** - pozwala wyświetlić konkretną pozycję, jej autora, udostępnia możliwość sprawdzenia okładki i spisu treści wybranej książki.

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
void goBackToBookList()\
void updateRentButtonText(Book bookDetails)\
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
**Konstruktor:** BookService(BookRepository bookRepository, AuthorRepository authorRepository) \
**Metody:**\
ReturnModel addBook(Book book, Member member)\
List<Book> getFreeBooks()\
Book getBookById(int id)\
List<Book> getAllBooks()\
List<Book> getBooksByTitle(String title)\
List<Book> getBooksByAuthor(Author author)\
List<Book> getBooksByCategory(String category)

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
