package teamdropin.server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join(Member member){
        validateDuplicateEmail(member.getUsername());
        validateDuplicateNickname(member.getNickname());
        memberRepository.save(member);
        return member.getId();
    }

    public Member getMember(Long id){
        Optional<Member> optionalMember = memberRepository.findById(id);
        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    public void validateDuplicateEmail(String email) {
        memberRepository.findByUsername(email)
                .ifPresent(m -> {
                    throw new BusinessLogicException(ExceptionCode.USERNAME_EXISTS);
                });
    }

    public void validateDuplicateNickname(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
                });
    }

    public String createRandomNickname(){
        String[] arr = new String[]{"풀업하는","머슬업하는","월볼샷하는","토투바하는","클린하는","스내치하는","스쿼트하는","로잉타는"};
        String anonymous = "크로스핏터";
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
        Random random = new Random();
        int randomNum = random.nextInt(arr.length);
        return arr[randomNum] + anonymous + uuid;
    }
}
