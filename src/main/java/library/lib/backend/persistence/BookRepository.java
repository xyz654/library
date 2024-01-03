package library.lib.backend.persistence;

import library.lib.backend.models.Author;
import library.lib.backend.models.Book;
import library.lib.backend.models.Category;
import library.lib.backend.models.ReturnModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("Select b from Book b where b.title = ?1")
    List<Book> findByTitle(String title);

    @Query("Select b from Book b where b.author = ?1")
    List<Book> findByAuthor(Author author);

    @Query("Select b from Book b where b.category = ?1")
    List<Book> findByCategory(Category category);

    @Query("Select b from Book b where b.loaner = null")
    List<Book> findByNullMember();

    Optional<Book> findById(int id);

}
