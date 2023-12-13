package library.lib.backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book implements ReturnObject {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    @OneToOne
    private Author author;
    private String description;
    private String bookCover;
    private String category;
    @OneToOne
    private Member loaner;
    @OneToMany
    private List<Rate> reviews = new ArrayList<>();

    private String tableOfContents;

    public Book() {
    }

    public Book(String title, Author author, String description, String bookCover, String tableOfContents) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookCover = bookCover;
        this.tableOfContents = tableOfContents;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
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

    private String getTableOfContents() {
        return tableOfContents;
    }

    public List<Rate> getReviews() {
        return reviews;
    }

    public void addReview(Rate review) {
        reviews.add(review);
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title='" + title + ", author='" + author + ", description='" + description + ", bookCover='" + bookCover + ", loaner=" + loaner + '}';
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
