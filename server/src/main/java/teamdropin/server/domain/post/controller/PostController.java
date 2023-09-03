package teamdropin.server.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.domain.post.dto.CreatePostRequest;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.service.PostService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;


    /**
     * 게시글 생성
     */
    @PostMapping("/post")
    public ResponseEntity<URI> createPost(@AuthenticationPrincipal Member member,
                                          @RequestBody CreatePostRequest createPostRequest){
        memberService.findVerifyMember(member.getUsername());
        Post createdPost = createPostRequest.toEntity(createPostRequest);
        postService.createPost(member,createdPost);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
