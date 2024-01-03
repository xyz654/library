package library.lib.backend.persistence;

import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.Rate;
import library.lib.backend.models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatesRepository extends JpaRepository<Rate, Integer> {

    @Query("Select r from Rate r where r.member = ?1 and r.book = ?2")
    List<Rate> findByMemberAndBook(Member member, Book book);

    @Query("Select r from Rate r where r.book.id = ?1")
    List<Rate> findByBookId(Integer bookId);

    @Query("Select avg(r.points), count(r.points), r.book from Rate r where r.book.id = ?1 group by r.book")
    List<Object[]> findAverageRatings();


}
