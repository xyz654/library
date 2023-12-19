package library.lib.backend.models;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@lombok.Setter
public class Author {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String description;
    private String nick;

    @OneToMany
    private List<Book> booksWritten =new ArrayList();

    public Author() {
    }

    public Author(String name, String description, String nick, List<Book> booksWritten) {
        this.name = name;
        this.description = description;
        this.nick = nick;
        this.booksWritten = booksWritten;
    }

    public Author(String name, String description, String nick) {
        this.name = name;
        this.description = description;
        this.nick = nick;
    }

    public void addBook(Book book){
        booksWritten.add(book);
    }

    public void removeBook(Book book){
        booksWritten.remove(book);
    }

}
