package library.lib.Backend.configurators;

import library.lib.Backend.models.Member;
import library.lib.Backend.persistence.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class MemberConfigurator {
    @Bean
    CommandLineRunner commandLineRunner(MemberRepository memberRepository) {
        return args ->{
            if(memberRepository.count() == 0){
                Member member = new Member("admin", "admin@admin.admin", "zaq1@WSX");
                memberRepository.save(member);
            }
        };
    }
}
