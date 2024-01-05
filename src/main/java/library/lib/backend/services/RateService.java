package library.lib.backend.services;

import library.lib.backend.models.*;
import library.lib.backend.persistence.MemberRepository;
import library.lib.backend.persistence.RatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RateService {
    private final RatesRepository ratesRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public RateService(RatesRepository ratesRepository, MemberRepository memberRepository) {
        this.ratesRepository = ratesRepository;
        this.memberRepository = memberRepository;
    }

    public ReturnModel addReview(Rate rate) {
        if (rate.getMember() == null) {
            return new ReturnModel(null, "User not logged in", ReturnCodes.USER_ERROR);
        }
        if (rate.getBook() == null) {
            return new ReturnModel(null, "Book not found", ReturnCodes.USER_ERROR);
        }
        if (rate.getPoints() < 0 || rate.getPoints() > 5) {
            return new ReturnModel(null, "Rate must be between 0 and 5", ReturnCodes.USER_ERROR);
        }
        if (ratesRepository.findByMemberAndBook(rate.getMember(), rate.getBook()).size() > 0) {
            return new ReturnModel(null, "User already reviewed this book", ReturnCodes.USER_ERROR);
        }
        ratesRepository.save(rate);
        return new ReturnModel(rate, "Review added", ReturnCodes.OK);
    }

    public List<Rate> getReviewsByBookId(Integer bookId) {
        return ratesRepository.findByBookId(bookId);
    }

    public List<Statistics> getBookStatistics() {
        List<Object[]> list = ratesRepository.findAverageRatings();
        List<Statistics> stats = new ArrayList<>();
        for (Object[] o : list) {
            Statistics s = new Statistics();
            s.setAverage((Double) o[0]);
            s.setCount((Integer) o[1]);
            s.setBook((Book) o[2]);
            stats.add(s);
        }
        return stats;
    }
}
