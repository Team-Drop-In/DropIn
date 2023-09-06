package teamdropin.server.domain.member.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import teamdropin.server.domain.member.dto.*;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.security.utils.CustomAuthorityUtils;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final CustomAuthorityUtils authorityUtils;

    public Member toMember(MemberSignUpRequestDto memberSignUpRequestDto){
        return Member.builder()
                .username(memberSignUpRequestDto.getUsername())
                .password(memberSignUpRequestDto.getPassword())
                .name(memberSignUpRequestDto.getName())
                .nickname(memberSignUpRequestDto.getNickname())
                .gender(memberSignUpRequestDto.getGender())
                .oauthProvider("dropin")
                .roles(authorityUtils.createUserRoles())
                .build();
    }

    public MemberSignUpResponseDto memberToMemberSignUpResponseDto(Member member){
        return MemberSignUpResponseDto.builder()
                .id(member.getId())
                .build();
    }

    public MyInfoResponseDto memberToGetMyInfoResponseDto(Member member){
        return MyInfoResponseDto.builder()
                .username(member.getUsername())
                .name(member.getName())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .profileImageUrl(member.getProfileImageUrl())
                .oauthProvider(member.getOauthProvider())
                .build();
    }
    public GetMemberResponseDto memberToGetMemberResponseDto(Member member){
        return GetMemberResponseDto.builder()
                .nickname(member.getNickname())
                .gender(member.getGender())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
