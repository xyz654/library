package library.lib.backend.services;

import library.lib.backend.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("pokerpythonproject@gmail.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);
    }

    public void sendBookReminderMail(Book book) {
        String mail = book.getLoaner().getEmail();
        String title = book.getTitle();
        String text = "You've been loaning the book " + title + " for 30 days now. Please return it as soon as possible.";
        this.sendEmail(mail, title, text);
    }

    public void sendRentConfirmationMail(Book book) {
        String mail = book.getLoaner().getEmail();
        String title = book.getTitle();
        String text = "You've rented the book " + title + ". Please return it within 30 days.";
        this.sendEmail(mail, title, text);
    }

    public void sendUpInQueueMail(Book book) {
        String mail = book.getLoaner().getEmail();
        String title = book.getTitle();
        String text = title + " books is ready for your pickup, please come to the library to get it. ";
        this.sendEmail(mail, title, text);
    }

}
