package library.lib.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics {
    private Double average;
    private Integer count;
    private Book book;
    private Integer day;
    private Integer month;
    private Integer year;

    public Statistics(Double average, Integer count, Book book) {
        this.average = average;
        this.count = count;
        this.book = book;
    }

    public Statistics(Integer count, Integer day, Integer month, Integer year) {
        this.count = count;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Statistics(){

    }

    @Override
    public String toString() {
        if(book != null)
            return "Statistics{" +
                    "average=" + average +
                    ", count=" + count +
                    ", book=" + book +
                    '}';
        else
            return "Statistics{" +
                    "count=" + count +
                    ", day=" + day +
                    ", month=" + month +
                    ", year=" + year +
                    '}';
    }
}
