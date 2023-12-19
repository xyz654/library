package library.lib.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Getter
@Setter
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

}
