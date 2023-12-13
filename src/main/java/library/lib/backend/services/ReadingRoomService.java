package library.lib.backend.services;

import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.ReadingRoom;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.persistence.ReadingRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReadingRoomService {

    private final ReadingRoomRepository readingRoomRepository;
    public ReadingRoomService(ReadingRoomRepository readingRoomRepository) {
        this.readingRoomRepository = readingRoomRepository;
    }

    public ReturnModel rentBook(Book book, Member member){

        if(member == null){
            return new ReturnModel(null, "User not logged in", 202);
        }

        if(book == null){
            return new ReturnModel(null, "Book not found", 202);
        }

        if(book.getLoaner() != null){
            return new ReturnModel(null, "Book already rented", 202);
        }

        try{

            ReadingRoom readingRoom = new ReadingRoom(member, book, new Date());
            readingRoomRepository.save(readingRoom);
            book.setLoaner(member);
            member.addBook(book);
            return new ReturnModel(book, "Book rented", 200);
        }catch(Exception e){
            log.info(String.valueOf(e));
            return new ReturnModel(null, "Error", 500);
        }
    }

    List<Book> getRentedBooksByMember(Member member){
        return readingRoomRepository.getRentedBooks(member);
    }

    public void returnBook(Book book, Member member){
        if(member.getBooksLoaned().contains(book)){
            member.removeBook(book);
        }
        if(book.getLoaner().equals(member)){
            book.setLoaner(null);
        }
        readingRoomRepository.getReadingRoom(book, member).ifPresent(readingRoom -> {
            readingRoom.setEndDate(new Date());
            readingRoomRepository.save(readingRoom);
        });
    }

    List<Book> getAllRentedBooks(){
        return readingRoomRepository.getCurrentRentedBooks();
    }
}
