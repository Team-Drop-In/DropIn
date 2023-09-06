package teamdropin.server.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.like.postLike.service.PostLikeService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.domain.post.dto.CreatePostRequest;
import teamdropin.server.domain.post.dto.GetPostResponseDto;
import teamdropin.server.domain.post.dto.UpdatePostRequestDto;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.mapper.PostMapper;
import teamdropin.server.domain.post.service.PostService;
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.util.UriCreator;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {

    private final static String POST_DEFAULT_URL = "/api/post";

    private final PostService postService;
    private final MemberService memberService;
    private final PostMapper postMapper;
    private final PostLikeService postLikeService;


    /**
     * 게시글 생성
     */
    @PostMapping("/post")
    public ResponseEntity<URI> createPost(@AuthenticationPrincipal Member member,
                                          @RequestBody CreatePostRequest createPostRequest){
        Post createdPost = createPostRequest.toEntity(createPostRequest);
        Long postId = postService.createPost(member, createdPost);
        URI location = UriCreator.createUri(POST_DEFAULT_URL, postId);
        return ResponseEntity.created(location).build();
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/post/{id}")
    public ResponseEntity<SingleResponseDto> getPost(@PathVariable("id") Long postId,
                                                     @AuthenticationPrincipal Member member){
        Post post = postService.getPost(postId, member);
        GetPostResponseDto getPostResponseDto = postMapper.postToGetPostResponseDto(post);
        boolean checkLike = postLikeService.checkLike(member,postId);
        getPostResponseDto.setCheckLike(checkLike);
        return new ResponseEntity<>(new SingleResponseDto<>(getPostResponseDto), HttpStatus.OK);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Void> updatePost(@AuthenticationPrincipal Member member,
                                           @PathVariable("id") Long postId,
                                           @RequestBody UpdatePostRequestDto updatePostRequestDto){
        Post post = postService.updatePost(postId,updatePostRequestDto,member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long postId,
                                           @AuthenticationPrincipal Member member){
        postService.deletePost(postId, member);
        return ResponseEntity.noContent().build();
    }
}
