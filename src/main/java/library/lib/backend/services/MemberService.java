package library.lib.backend.services;

import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.persistence.MemberRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        System.out.println(memberRepository);
    }


    public ReturnModel register(String username, String password, String email) {
        try{
            if(memberRepository.count() == 0){
                Member member = new Member("admin", "admin@admin.admin", "zaq1@WSX", Permissions.ADMIN);
                memberRepository.save(member);
                Member member2 = new Member("worker", "worker@worker.worker", "zaq1@WSX", Permissions.WORKER);
                memberRepository.save(member2);
            }

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

    public List<Member> getLatestMembers(int memberCount) {
        List<Member> members = memberRepository.findAll();
        if(members.size() < memberCount){
            return members;
        }
        return members.subList(members.size()-memberCount, members.size());
    }

    public Optional<Member> getMember(Member member){
        return memberRepository.findById(member.getId());
    }


}
