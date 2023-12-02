package library.lib.backend.services;

import library.lib.backend.models.Member;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.persistence.MemberRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public ReturnModel register(String username, String password, String email) {
        try{
            log.info("Email: "+email+" Password: "+password+" Username: "+username);
            Optional<Member> user = memberRepository.findByEmail(email);
            if(user.isPresent()) {
                return new ReturnModel(null, "User already exists", 202);
            }
            Member newMember = new Member(username, email, password);
            if(newMember.getPassword() == null || newMember.getEmail() == null ){
                return new ReturnModel(null, "Invalid email or password", 202);
            }
            memberRepository.save(newMember);
            return new ReturnModel(newMember, "User registered", 200);

        }catch(Exception e){
            log.info(String.valueOf(e));
            return new ReturnModel(null, "Error", 500);
        }

    }

    public ReturnModel login(String email, String password) {
        Optional<Member> user = memberRepository.findByEmail(email);
        if(user.isPresent() && (user.get().getPassword().equals(password))) {
                log.info("User logged in");
                return new ReturnModel(user.get(), "User logged in", 200);

        }
        log.info("User not logged in");
        return new ReturnModel(null, "User not logged in", 202);
    }
}
