package library.lib.backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private Member member;

    @OneToOne
    private Book book;

    private Date start_date;

    private Date end_date;

    public History() {
    }

    public History(Member member, Book book, Date start_date, Date end_date) {
        this.member = member;
        this.book = book;
        this.start_date = start_date;
        this.end_date = end_date;
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

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }


}
