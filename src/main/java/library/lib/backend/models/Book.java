package library.lib.backend.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Book implements ReturnObject {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    @OneToOne
    private Author author;
    private String description;
    private String bookCover;
    @OneToOne
    private Category category;
    @OneToOne
    private Member loaner;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Rate> reviews = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Member> awaitingMembers = new ArrayList<>();
    private String tableOfContents;

    public Book() {
    }

    public Book(String title, Author author, String description, String bookCover, String tableOfContents, Category category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookCover = bookCover;
        this.tableOfContents = tableOfContents;
        this.category = category;
    }

    public void addReview(Rate review) {
        reviews.add(review);
    }

    public void addAwaitingMember(Member member) {
        awaitingMembers.add(member);
    }

    public void removeAwaitingMember(Member member) {
        List<Member> newAwaitingMembers = new ArrayList<>();
        for (Member m : awaitingMembers) {
            if (m.getId() != member.getId()) {
                newAwaitingMembers.add(m);
            }
        }
        awaitingMembers = newAwaitingMembers;
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
