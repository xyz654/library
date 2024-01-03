package library.lib;

import library.lib.backend.models.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {
    @Test
    void testMember() {
        Member member = new Member("email", "userName", "password");
        assertEquals(null, member.getEmail());
        assertEquals("email", member.getName());
        assertEquals("password", member.getPassword());
        Member correctMember = new Member("member", "member@member.member", "password123");
        assertEquals("member", correctMember.getName());
        assertEquals("member@member.member", correctMember.getEmail());
        assertEquals("password123", correctMember.getPassword());
    }

}
