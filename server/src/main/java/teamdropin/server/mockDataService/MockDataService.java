package teamdropin.server.mockDataService;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teamdropin.server.domain.member.entity.Gender;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.domain.post.entity.Category;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.domain.post.service.PostService;
import teamdropin.server.security.utils.CustomAuthorityUtils;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MockDataService {

    private static final String REASSIGN_EMAIL = "reassign@dropin.com";
    private final PasswordEncoder passwordEncoder;

    private final CustomAuthorityUtils customAuthorityUtils;
    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private final PostService postService;

    private final PostRepository postRepository;

    /**
     * 테스트용
     */
    @PostConstruct
    public void createReassignMember(){

        Member reassignMember =
                Member.builder()
                        .username(REASSIGN_EMAIL)
                        .password(passwordEncoder.encode("Test135!"))
                        .nickname("탈퇴한회원")
                        .name("탈퇴한회원")
                        .oauthProvider("dropin")
                        .roles(customAuthorityUtils.createManagerRoles())
                        .gender(Gender.NOT_SELECT)
                        .build();
        memberRepository.save(reassignMember);

        for(int i = 0; i < 30; i++){
            Post mockPost =
                    Post.builder()
                            .title("제목 입니다." + i)
                            .body("본문 입니다." + i)
                            .category(Category.QUESTION)
                            .build();

            mockPost.addMember(reassignMember);
            postRepository.save(mockPost);
        }
    }

}
