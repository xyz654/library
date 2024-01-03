package library.lib.backend.services;


import library.lib.backend.models.*;
import library.lib.backend.persistence.AuthorRepository;
import library.lib.backend.persistence.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public ReturnModel addBook(Book book, Member member) {
        if (member == null) {
            return new ReturnModel(null, "User not logged in", ReturnCodes.USER_ERROR);
        }

        if (member.getPermission() != Permissions.ADMIN && member.getPermission() != Permissions.WORKER) {
            return new ReturnModel(null, "User not authorized", ReturnCodes.USER_ERROR);
        }
        bookRepository.save(book);
        Author author = book.getAuthor();
        author.addBook(book);
        authorRepository.save(author);
        return new ReturnModel(book, "Book added", ReturnCodes.OK);

    }

    public List<Book> getFreeBooks() {
        return bookRepository.findByNullMember();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

}
