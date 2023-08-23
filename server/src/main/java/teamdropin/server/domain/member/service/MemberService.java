package teamdropin.server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public void join(Member member){
        validateDuplicateEmail(member.getUsername());
        validateDuplicateNickname(member.getNickname());
        memberRepository.save(member);
    }

    public void validateDuplicateEmail(String email) {
        memberRepository.findByUsername(email)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일 입니다.");
                });
    }

    public void validateDuplicateNickname(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
                });
    }
}
