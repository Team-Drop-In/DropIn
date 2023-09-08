package teamdropin.server.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.comment.dto.CommentResponseDto;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.comment.mapper.CommentMapper;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.domain.post.dto.CreatePostRequest;
import teamdropin.server.domain.post.dto.GetAllPostResponseDto;
import teamdropin.server.domain.post.dto.GetPostResponseDto;
import teamdropin.server.domain.post.dto.UpdatePostRequestDto;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.mapper.PostMapper;
import teamdropin.server.domain.post.service.PostService;
import teamdropin.server.global.dto.MultiResponseDto;
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.util.UriCreator;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {

    private final static String POST_DEFAULT_URL = "/api/post";

    private final PostService postService;
    private final MemberService memberService;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final LikeService likeService;


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
        GetPostResponseDto getPostResponseDto = translateGetPostResponseDto(member, post);
        return new ResponseEntity<>(new SingleResponseDto<>(getPostResponseDto), HttpStatus.OK);
    }

    private GetPostResponseDto translateGetPostResponseDto(Member member, Post post) {
        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : comments){
            CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(comment);
            boolean checkCommentLike = likeService.checkCommentLike(member, comment.getId());
            commentResponseDto.setCheckCommentLike(checkCommentLike);
            commentResponseDtoList.add(commentResponseDto);
        }
        GetPostResponseDto getPostResponseDto = postMapper.postToGetPostResponseDto(post, commentResponseDtoList);
        boolean checkPostLike = likeService.checkPostLike(member, post.getId());
        getPostResponseDto.setCheckPostLike(checkPostLike);
        return getPostResponseDto;
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

    @GetMapping("/posts")
    public ResponseEntity<MultiResponseDto> getPosts(@Positive @RequestParam int page,
                                                     @Positive @RequestParam int size){

        Page<Post> pagePosts = postService.getAllPosts(page -1 , size);
        List<Post> posts = pagePosts.getContent();
        List<GetAllPostResponseDto> getAllPostResponseDtoList = postMapper.postToGetAllPostResponseDtoList(posts);
        return new ResponseEntity<>(new MultiResponseDto<>(getAllPostResponseDtoList,pagePosts),HttpStatus.OK);
    }
}
