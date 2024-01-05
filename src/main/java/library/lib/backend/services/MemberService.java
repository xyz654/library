package library.lib.backend.services;

import library.lib.backend.models.*;
import library.lib.backend.persistence.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public ReturnModel register(String username, String password, String email) {
        try {
            if (memberRepository.count() == 0) {
                Member member = new Member("admin", "admin@admin.admin", "zaq1@WSX", Permissions.ADMIN);
                memberRepository.save(member);
                Member member2 = new Member("worker", "worker@worker.worker", "zaq1@WSX", Permissions.WORKER);
                memberRepository.save(member2);
            }

            log.info("Email: " + email + " Password: " + password + " Username: " + username);
            Optional<Member> user = memberRepository.findByEmail(email);
            if (user.isPresent()) {
                return new ReturnModel(null, "User already exists", ReturnCodes.USER_ERROR);
            }
            Member newMember = new Member(username, email, password);
            if (newMember.getPassword() == null || newMember.getEmail() == null) {
                return new ReturnModel(null, "Invalid email or password", ReturnCodes.USER_ERROR);
            }
            memberRepository.save(newMember);
            return new ReturnModel(newMember, "User registered", ReturnCodes.OK);

        } catch (Exception e) {
            log.error(String.valueOf(e));
            return new ReturnModel(null, "Error", ReturnCodes.ERROR);
        }

    }

    public ReturnModel login(String email, String password) {
        Optional<Member> user = memberRepository.findByEmail(email);
        if (user.isPresent() && (user.get().getPassword().equals(password))) {
            log.info("User logged in");
            return new ReturnModel(user.get(), "User logged in", ReturnCodes.OK);
        }
        log.info("User not logged in");
        return new ReturnModel(null, "User not logged in", ReturnCodes.USER_ERROR);
    }

    public List<Member> getLatestMembers(int memberCount) {
        List<Member> members = memberRepository.findAll();
        if (members.size() < memberCount) {
            return members;
        }
        return members.subList(members.size() - memberCount, members.size());
    }

    public Optional<Member> getMember(Member member) {
        return memberRepository.findById(member.getId());
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void setPermission(Member member, Permissions permission) {
        member.setPermission(permission);
        memberRepository.save(member);
    }

    public void setFavouriteCategory(Member member, Category category) {
        member.setFavouriteCategory(category);
        memberRepository.save(member);
    }


}
