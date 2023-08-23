package teamdropin.server.domain.member.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import teamdropin.server.domain.member.dto.GetMemberResponseDto;
import teamdropin.server.domain.member.dto.MemberSignUpRequestDto;
import teamdropin.server.domain.member.dto.MemberSignUpResponseDto;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.security.utils.CustomAuthorityUtils;

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

    public MemberSignUpResponseDto memberToMemberSignUpResponseDto(Member member){
        return MemberSignUpResponseDto.builder()
                .id(member.getId())
                .build();
    }

    public GetMemberResponseDto memberToGetMemberResponseDto(Member member){
        return GetMemberResponseDto.builder()
                .username(member.getUsername())
                .name(member.getName())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
