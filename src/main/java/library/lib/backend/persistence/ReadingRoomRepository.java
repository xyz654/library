package library.lib.backend.persistence;

import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.ReadingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface ReadingRoomRepository extends JpaRepository<ReadingRoom, Integer> {
    @Query("Select r.book from ReadingRoom r where r.end_date is not null and r.member = ?1")
    List<Book> getRentedBooksByUser(Member member);

    @Query("Select r from ReadingRoom r where r.end_date is null and r.member = ?1")
    List<ReadingRoom> getCurrentReadingBooksByUser(Member member);

    @Query("Select r from ReadingRoom r where r.end_date is not null and r.member = ?1")
    List<ReadingRoom> getAllRentedBooksByUser(Member member);

    @Query("Select r.book from ReadingRoom r where r.end_date is null")
    List<Book> getCurrentRentedBooks();
    @Query("Select r.book from ReadingRoom r where r.end_date is not null")
    List<Book> getPreviouslyRentedBooks();
    @Query("Select r from ReadingRoom r where r.book = ?1 and r.member = ?2 order by r.id desc")
    List<ReadingRoom> getReadingRoom(Book book, Member member);
    @Query("SELECT r FROM ReadingRoom r WHERE r.end_date is null")
    List<ReadingRoom> getAllReadingRooms();

    @Query("Select count(r.book), day(r.end_date), month(r.end_date), year(r.end_date) from ReadingRoom r where r.end_date is not null " +
            "group by day(r.end_date), month(r.end_date), year(r.end_date) order by year(r.end_date), month(r.end_date), day(r.end_date)")
    List<Object[]> getRentedBooksByDate();

}
