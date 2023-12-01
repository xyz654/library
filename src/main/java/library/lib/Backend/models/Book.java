package library.lib.Backend.models;

import com.google.gson.JsonObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private int id;

    private String title;
    private String author;
    private String description;

    private String bookCover;
    @OneToOne
    private Member loaner;

    public Book() {
    }

    public Book(String title, String author, String description, String bookCover) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookCover = bookCover;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getBookCover() {
        return bookCover;
    }

    public Member getLoaner() {
        return loaner;
    }

    public void setLoaner(Member loaner) {
        this.loaner = loaner;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title +
                ", author='" + author +
                ", description='" + description +
                ", bookCover='" + bookCover +
                ", loaner=" + loaner +
                '}';
    }

    public String toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("title", title);
        json.addProperty("author", author);
        json.addProperty("description", description);
        json.addProperty("bookCover", bookCover);
        json.addProperty("loaner", loaner.toString());
        return json.toString();
    }

}
