package library.lib.backend.persistence;

import library.lib.backend.models.Book;
import library.lib.backend.models.Member;
import library.lib.backend.models.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(int id);

    @Modifying
    @Query("UPDATE Member m SET m.permission = ?2 WHERE m.id = ?1")
    void updateMemberPermission( int memberId, Permissions newPermission);


}
