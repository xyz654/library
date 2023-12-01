package library.lib.Backend.dto;

import library.lib.Backend.models.Member;

public record MemberDto(int id, String email, String userName, String password) {
    public static MemberDto from(Member member){
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPassword()
        );
    }
}