package library.lib.backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Rate {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Member member;
    @OneToOne
    private Book book;

    private int points;

    private String description;

    public Rate() {
    }

    public Rate(Member member, Book book, int points, String description) {
        this.member = member;
        this.book = book;
        this.points = points;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }
}
