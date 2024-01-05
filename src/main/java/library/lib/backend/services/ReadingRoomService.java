package library.lib.backend.services;

import library.lib.backend.models.*;
import library.lib.backend.persistence.BookRepository;
import library.lib.backend.persistence.MemberRepository;
import library.lib.backend.persistence.ReadingRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReadingRoomService {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private final ReadingRoomRepository readingRoomRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final EmailSenderService EmailService;

    public ReadingRoomService(ReadingRoomRepository readingRoomRepository, MemberRepository memberRepository, BookRepository bookRepository, EmailSenderService emailService) {
        this.readingRoomRepository = readingRoomRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        EmailService = emailService;

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
            if (member.checkForNotifications()) {
                EmailService.sendRentConfirmationMail(book);
            }
            return new ReturnModel(book, "Book rented", ReturnCodes.OK);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new ReturnModel(null, "Error", ReturnCodes.ERROR);
        }
    }

    public ReturnModel reserveBook(Book book, Member member) {
        if (member == null) {
            return new ReturnModel(null, "User not logged in", ReturnCodes.USER_ERROR);
        }

        if (book == null) {
            return new ReturnModel(null, "Book not found", ReturnCodes.USER_ERROR);
        }

        if (book.getLoaner() == null) {
            return new ReturnModel(null, "Book can be rented", ReturnCodes.USER_ERROR);
        }
        for(Member m : book.getAwaitingMembers()){
            if(m.getId() == member.getId()){
                return new ReturnModel(null, "User already has book", ReturnCodes.USER_ERROR);
            }
        }
        for(Book b : member.getRequestedBooks()){
            if(b.getId() == book.getId()){
                return new ReturnModel(null, "User already has book", ReturnCodes.USER_ERROR);
            }
        }
        if(book.getLoaner().getId() == member.getId()){
            return new ReturnModel(null, "User already has book", ReturnCodes.USER_ERROR);
        }
        try {
            book.addAwaitingMember(member);
            bookRepository.save(book);
            member.addRequestedBook(book);
            memberRepository.save(member);
            return new ReturnModel(book, "Book rented", ReturnCodes.OK);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new ReturnModel(null, "Error", ReturnCodes.ERROR);
        }
    }

    public ReturnModel cancelReservation(Book book, Member member) {
        if (member == null) {
            return new ReturnModel(null, "User not logged in", ReturnCodes.USER_ERROR);
        }

        if (book == null) {
            return new ReturnModel(null, "Book not found", ReturnCodes.USER_ERROR);
        }

        if (book.getLoaner() == null) {
            return new ReturnModel(null, "Book can be rented", ReturnCodes.USER_ERROR);
        }

        if (!book.getAwaitingMembers().contains(member)) {
            return new ReturnModel(null, "User does not have book", ReturnCodes.USER_ERROR);
        }

        try {
            book.removeAwaitingMember(member);
            bookRepository.save(book);
            member.removeRequestedBook(book);
            memberRepository.save(member);
            return new ReturnModel(book, "Book rented", ReturnCodes.OK);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new ReturnModel(null, "Error", ReturnCodes.ERROR);
        }
    }

    public List<Book> getRentedBooksByMember(Member member) {
        return readingRoomRepository.getRentedBooksByUser(member);
    }


    public void returnBook(Book book, Member member) {
        member.removeBook(book);
        memberRepository.save(member);
        book.setLoaner(null);
        bookRepository.save(book);
        List<ReadingRoom> readingRooms = readingRoomRepository.getReadingRoom(book, member);
        ReadingRoom readingRoom = readingRooms.get(0);
        readingRoom.setEnd_date(new Date());
        readingRoomRepository.save(readingRoom);

        if (book.getAwaitingMembers().size() > 0) {
            Member nextMember = book.getAwaitingMembers().get(0);
            book.removeAwaitingMember(nextMember);
            book.setLoaner(nextMember);
            bookRepository.save(book);
            nextMember.addBook(book);
            nextMember.removeRequestedBook(book);
            if (book.getLoaner().checkForNotifications()) {
                EmailService.sendUpInQueueMail(book);
            }
            memberRepository.save(nextMember);
            readingRoomRepository.save(new ReadingRoom(nextMember, book, new Date()));
        }
    }

    public List<Book> getAllRentedBooks() {
        return readingRoomRepository.getCurrentRentedBooks();
    }

    public List<Book> getAllReturnedBooks() {
        return readingRoomRepository.getPreviouslyRentedBooks();
    }

    public List<ReadingRoom> getUserHistory(Member member) {
        return readingRoomRepository.getAllRentedBooksByUser(member);
    }

    public List<Book> getUserHistoryWithFilters(Filters filters) {
        String customQuery = "SELECT r FROM ReadingRoom r ";
        customQuery += "WHERE r.member = '" + filters.getMember().getId() + "' ";
        if (filters.getAuthor() != null) {
            customQuery += " AND r.book.author = '" + filters.getAuthor().getId() + "'";
        }
        if (filters.getCategory() != null) {
            customQuery += " AND r.book.category = '" + filters.getCategory().getId() + "'";
        }

        if (filters.getTitle() != null) {
            customQuery += " AND r.book.title = '" + filters.getTitle() + "'";
        }


        EntityManager em = emf.createEntityManager();
        List<Book> books = em.createQuery(customQuery).getResultList();
        em.close();
        return books;
    }


    public List<ReadingRoom> getCurrentRentedBooksByMember(Member member) {
        return readingRoomRepository.getCurrentReadingBooksByUser(member);
    }

    public List<Statistics> getStats(){
        List<Object[]> stats = readingRoomRepository.getRentedBooksByDate();
        List<Statistics> statistics = new ArrayList<>();
        for (Object[] stat : stats) {
            Statistics statistics1 = new Statistics();
            statistics1.setCount(Math.toIntExact((Long) stat[0]));
            statistics1.setDay((Integer) stat[1]);
            statistics1.setMonth((Integer) stat[2]);
            statistics1.setYear((Integer) stat[3]);
            statistics.add(statistics1);
        }
        return statistics;

//        return readingRoomRepository.getRentedBooksByDate();
    }
}