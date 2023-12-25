package library.lib.backend.services;

import library.lib.backend.models.*;
import library.lib.backend.persistence.BookRepository;
import library.lib.backend.persistence.MemberRepository;
import library.lib.backend.persistence.ReadingRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReadingRoomService {

    private final ReadingRoomRepository readingRoomRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public ReadingRoomService(ReadingRoomRepository readingRoomRepository, MemberRepository memberRepository, BookRepository bookRepository) {
        this.readingRoomRepository = readingRoomRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;

    }

    public ReturnModel rentBook(Book book, Member member) {
        if (member == null) {
            return new ReturnModel(null, "User not logged in", ReturnCodes.USER_ERROR);
        }

        if (book == null) {
            return new ReturnModel(null, "Book not found", ReturnCodes.USER_ERROR);
        }

        if (book.getLoaner() != null) {
            return new ReturnModel(null, "Book already rented", ReturnCodes.USER_ERROR);
        }

        try {

            ReadingRoom readingRoom = new ReadingRoom(member, book, new Date());
            readingRoomRepository.save(readingRoom);
            book.setLoaner(member);
            member.addBook(book);
            memberRepository.save(member);
            bookRepository.save(book);
            return new ReturnModel(book, "Book rented", ReturnCodes.OK);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new ReturnModel(null, "Error", ReturnCodes.ERROR);
        }
    }

    List<Book> getRentedBooksByMember(Member member) {
        return readingRoomRepository.getRentedBooks(member);
    }

    public void returnBook(Book book, Member member) {
        if (member.getBooksLoaned().contains(book)) {
            member.removeBook(book);
            memberRepository.save(member);
        }
        if (book.getLoaner() != null) {
            book.setLoaner(null);
            bookRepository.save(book);
        }
        readingRoomRepository.getReadingRoom(book, member).ifPresent(readingRoom -> {
            readingRoom.setEnd_date(new Date());
            readingRoomRepository.save(readingRoom);
        });
    }

    List<Book> getAllRentedBooks() {
        return readingRoomRepository.getCurrentRentedBooks();
    }

    public List<Book> getBookHistory(Member member) {
        return readingRoomRepository.getBookHistory(member);
    }

    public List<ReadingRoom> getCurrentRentedBooks(Member member) {
        return readingRoomRepository.getCurrentReadingBooks(member);
    }
}