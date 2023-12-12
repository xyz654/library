package library.lib.backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String description;
    private String nick;

    @OneToMany
    private List<Book> booksWritten;

    public Author() {
    }

    public Author(String name, String description, String nick, List<Book> booksWritten) {
        this.name = name;
        this.description = description;
        this.nick = nick;
        this.booksWritten = booksWritten;
    }

    public void addBook(Book book){
        booksWritten.add(book);
    }

    public void removeBook(Book book){
        booksWritten.remove(book);
    }

    public List<Book> getBooksWritten() {
        return booksWritten;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getNick() {
        return nick;
    }

}
