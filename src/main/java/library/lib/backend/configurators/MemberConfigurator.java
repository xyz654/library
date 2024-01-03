package library.lib.backend.configurators;

import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import library.lib.backend.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfigurator {
    @Autowired
    CommandLineRunner commandLineRunner(MemberRepository memberRepository) {
        return args ->{
            if(memberRepository.count() == 0){
                Member member = new Member("admin", "admin@admin.admin", "zaq1@WSX", Permissions.ADMIN);
                memberRepository.save(member);
                Member member2 = new Member("worker", "worker@worker.worker", "zaq1@WSX", Permissions.WORKER);
                memberRepository.save(member2);
            }
        };
    }
}
