package library.lib.Backend.services;

import library.lib.Backend.models.Member;
import library.lib.Backend.models.ReturnModel;
import library.lib.Backend.persistence.MemberRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public ReturnModel register(String username, String password, String email) {
        try{
            System.out.println("Email: "+email+" Password: "+password+" Username: "+username);
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
            System.out.println(e);
            return new ReturnModel(null, "Error", 500);
        }

    }

    public ReturnModel login(String email, String password) {
        Optional<Member> user = memberRepository.findByEmail(email);
        if(user.isPresent()) {
            if(user.get().getPassword().equals(password)) {
                System.out.println("User logged in");
                return new ReturnModel(user.get(), "User logged in", 200);
            }
        }
        System.out.println("User not logged in");
        return new ReturnModel(null, "User not logged in", 202);
    }
}
