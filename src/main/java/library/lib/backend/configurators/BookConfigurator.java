package library.lib.backend.configurators;

import library.lib.backend.models.Author;
import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.persistence.AuthorRepository;
import library.lib.backend.persistence.BookRepository;
import library.lib.backend.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfigurator {
    @Autowired
    CommandLineRunner commandLineRunner(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args ->{
           if(bookRepository.count() == 0){
               Author author = new Author("Adam Mickiewicz",
                       "Polish poet",
                       "Mickiewicz");
               Author author2 = new Author("Henryk Sienkiewicz",
                       "Polish writer", "Sienkiewicz");
               Book book = new Book("Pan Tadeusz", author,
                       "Pan taduesz",
                       "https://ecsmedia.pl/cdn-cgi/image/format=webp,/c/pan-tadeusz-b-iext135008338.jpg",
                       "cos ");
               Book book2 = new Book(
                       "Quo Vadis",
                          author2,
                          "Quo Vadis",
                       "https://ecsmedia.pl/c/quo-vadis-b-iext123393340.jpg",
                       "cos 2");
                authorRepository.save(author);
                authorRepository.save(author2);
                bookRepository.save(book);
                bookRepository.save(book2);
           }
        };
    }
}
