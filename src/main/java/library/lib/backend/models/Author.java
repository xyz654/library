package library.lib.backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
public class Author implements ReturnObject {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String description;
    private String nick;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Book> booksWritten = new ArrayList();

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

    public Author(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        booksWritten.add(book);
    }

    public void removeBook(Book book) {
        booksWritten.remove(book);
    }

    @Override
    public String toJson() throws JsonProcessingException {
        return "Author{" + "id=" + id + ", name='" + name + ", nick='" + nick + ", description='" + description + '}';
    }
}
