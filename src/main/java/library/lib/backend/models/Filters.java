package library.lib.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filters {

    private int minReviews;
    private int maxReviews;
    private int avgRating;
    private Category category;
    private Author author;
    private String title;
    private boolean free;
    private Member member;

    public Filters() {
    }

    public Filters(Category category, Author author, String title, boolean free) {
        this.category = category;
        this.author = author;
        this.title = title;
        this.free = free;
    }

    public Filters(Category category, Author author, String title, boolean free, Member member) {
        this.category = category;
        this.author = author;
        this.title = title;
        this.free = free;
        this.member = member;
    }
}
