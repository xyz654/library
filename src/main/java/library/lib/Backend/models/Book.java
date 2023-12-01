package library.lib.Backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Book implements ReturnObject {
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
        return "Book{" + "id=" + id + ", title='" + title + ", author='" + author + ", description='" + description + ", bookCover='" + bookCover + ", loaner=" + loaner + '}';
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(this);
        return json;
    }

}
