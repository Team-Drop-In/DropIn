package teamdropin.server.domain.member.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import teamdropin.server.domain.member.dto.MemberSignUpRequestDto;
import teamdropin.server.domain.member.dto.MemberSignUpResponseDto;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.entity.OauthProvider;
import teamdropin.server.domain.member.entity.Role;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final PasswordEncoder passwordEncoder;

    public Member toMember(MemberSignUpRequestDto memberSignUpRequestDto){
        return Member.builder()
                .email(memberSignUpRequestDto.getEmail())
                .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                .name(memberSignUpRequestDto.getName())
                .nickname(memberSignUpRequestDto.getNickname())
                .gender(memberSignUpRequestDto.getGender())
                .oauthProvider(OauthProvider.DROPIN)
                .role(Role.ROLE_USER)
                .build();
    }

    public MemberSignUpResponseDto toMemberSignUpResponseDto(Member member){
        return MemberSignUpResponseDto.builder()
                .id(member.getId())
                .build();
    }
}
