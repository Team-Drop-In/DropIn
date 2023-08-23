package teamdropin.server.domain.member.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import teamdropin.server.domain.member.dto.MemberSignUpRequestDto;
import teamdropin.server.domain.member.dto.MemberSignUpResponseDto;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.entity.OauthProvider;
import teamdropin.server.domain.member.entity.Role;
import teamdropin.server.security.utils.CustomAuthorityUtils;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final CustomAuthorityUtils authorityUtils;

    private final PasswordEncoder passwordEncoder;

    public Member toMember(MemberSignUpRequestDto memberSignUpRequestDto){
        return Member.builder()
                .username(memberSignUpRequestDto.getUsername())
                .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                .name(memberSignUpRequestDto.getName())
                .nickname(memberSignUpRequestDto.getNickname())
                .gender(memberSignUpRequestDto.getGender())
                .oauthProvider("DROPIN")
                .roles(authorityUtils.createUserRoles())
                .build();
    }

    public MemberSignUpResponseDto toMemberSignUpResponseDto(Member member){
        return MemberSignUpResponseDto.builder()
                .id(member.getId())
                .build();
    }
}
