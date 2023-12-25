package library.lib.backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Member implements ReturnObject {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Book> booksLoaned = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Rate> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Permissions permission = Permissions.MEMBER;


    public Member() {
    }

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = verifyEmail(email) ? email : null;
        this.password = verifyPassword(password) ? password : null;
    }

    public Member(String name, String email, String password, Permissions permission) {
        this.name = name;
        this.email = verifyEmail(email) ? email : null;
        this.password = verifyPassword(password) ? password : null;
        this.permission = permission;
    }

    public void addBook(Book book) {
        booksLoaned.add(book);
    }

    public void removeBook(Book book) {
        booksLoaned.remove(book);
    }

    public List<Book> getBooksLoaned() {
        return booksLoaned;
    }

    public void addReview(Rate review) {
        reviews.add(review);
    }

    public void removeReview(Rate review) {
        reviews.remove(review);
    }

    public boolean verifyEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public boolean verifyPassword(String password) {
        return password.length() >= 8;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + '}';
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
