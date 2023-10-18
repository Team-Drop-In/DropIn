package teamdropin.server.mockDataService;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.entity.BoxTag;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.comment.repository.CommentRepository;
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
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final BoxRepository boxRepository;

    /**
     * 테스트용
     */
    @PostConstruct
    public void createMockData() {

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

        int tmp = 31;
        for (int i = 0; i < 30; i++) {
            tmp--;
            Post mockPost =
                    Post.builder()
                            .title("제목 입니다." + i)
                            .body("본문 입니다." + tmp)
                            .category(Category.QUESTION)
                            .build();

            mockPost.addMember(reassignMember);
            postRepository.save(mockPost);

            for (int j = 10; j > 0; j--) {
                Comment mockComment =
                        Comment.builder()
                                .body("댓글 입니다." + i)
                                .build();

                mockComment.addMember(reassignMember);
                mockComment.addPost(mockPost);
                commentRepository.save(mockComment);

            }
        }
        for (int i = 0; i < 5; i++) {
            Box mockBox =
                    Box.builder()
                            .name("박스 " + i)
                            .location("부산")
                            .phoneNumber("00000000000")
                            .cost(20L)
                            .area(100L)
                            .barbellDrop(false)
                            .url("dropin.com")
                            .detail("널은 박스! 꺠끗한 시설")
                            .build();

            mockBox.addMember(reassignMember);
            boxRepository.save(mockBox);

            BoxTag mockBoxTag =
                    BoxTag.builder()
                            .tagName(String.valueOf(i))
                            .build();
        }
    }
}
