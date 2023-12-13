package library.lib.backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class ReadingRoom {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private Member member;

    @OneToOne
    private Book book;

    private Date start_date;

    private Date end_date;

    public ReadingRoom() {
    }
    public ReadingRoom(Member member, Book book, Date start_date) {
        this.member = member;
        this.book = book;
        this.start_date = start_date;
    }
    public ReadingRoom(Member member, Book book, Date start_date, Date end_date) {
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

    public Date getStartDate() {
        return start_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }


}
