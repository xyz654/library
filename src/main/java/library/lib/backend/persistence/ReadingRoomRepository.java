package library.lib.backend.persistence;

import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.ReadingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface ReadingRoomRepository extends JpaRepository<ReadingRoom, Integer> {
    @Query("Select r.book from ReadingRoom r where r.end_date != null and r.member = ?1")
    List<Book> getRentedBooksByUser(Member member);

    @Query("Select r from ReadingRoom r where r.end_date = null and r.member = ?1")
    List<ReadingRoom> getCurrentReadingBooksByUser(Member member);

    @Query("Select r from ReadingRoom r where r.member = ?1")
    List<ReadingRoom> getAllRentedBooksByUser(Member member);

    @Query("Select r.book from ReadingRoom r where r.end_date = null")
    List<Book> getCurrentRentedBooks();
    @Query("Select r.book from ReadingRoom r where r.end_date != null")
    List<Book> getPreviouslyRentedBooks();
    @Query("Select r from ReadingRoom r where r.book = ?1 and r.member = ?2")
    Optional<ReadingRoom> getReadingRoom(Book book, Member member);

}
