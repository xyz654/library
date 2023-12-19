package library.lib.backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Member implements ReturnObject {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String email;

    private String password;

    @OneToMany
    private List<Book> booksLoaned = new ArrayList<>();

    @OneToMany
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

    public void addBook(Book book){
        booksLoaned.add(book);
    }

    public void removeBook(Book book){
        booksLoaned.remove(book);
    }

    public List<Book> getBooksLoaned() {
        return booksLoaned;
    }

    public void addReview(Rate review){
        reviews.add(review);
    }

    public void removeReview(Rate review){
        reviews.remove(review);
    }

    public List<Rate> getReviews() {
        return reviews;
    }

    public int getId() {
        return id;
    }

    public boolean verifyEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            return true;
        }
        return false;
    }

    public boolean verifyPassword(String password) {
        if (password.length() >= 8) {
            return true;
        }
        return false;
    }

    public void setEmail(String email) {
        if (verifyEmail(email)) {
            this.email = email;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        if (verifyPassword(password)) {
            this.password = password;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Permissions getPermission() {
        return permission;
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
