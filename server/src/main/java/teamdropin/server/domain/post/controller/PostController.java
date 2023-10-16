package teamdropin.server.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import teamdropin.server.domain.post.dto.*;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.mapper.PostMapper;
import teamdropin.server.domain.post.service.PostService;
import teamdropin.server.global.dto.MultiResponseDto;
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.util.UriCreator;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "2. Post API", description = "Post API Document")
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


    @Operation(summary = "게시글 등록 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공 "),
            @ApiResponse(responseCode = "400", description = "게시글 등록 유효성 검증에 실패한 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우")
    })
    @PostMapping("/post")
    public ResponseEntity<URI> createPost(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                          @RequestBody @Valid CreatePostRequest createPostRequest){
        Post createdPost = createPostRequest.toEntity(createPostRequest);
        Long postId = postService.createPost(member, createdPost);
        URI location = UriCreator.createUri(POST_DEFAULT_URL, postId);
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "게시글 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공 "),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글의 경우")
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<SingleResponseDto> getPost(@PathVariable("id") Long postId,
                                                     @Parameter(hidden = true) @AuthenticationPrincipal Member member){
        GetPostResponseDto getPostResponseDto = postService.getPostQuery(postId, member);
        return new ResponseEntity<>(new SingleResponseDto<>(getPostResponseDto), HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 수정 유효성 검증에 실패한 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글의 경우"),
    })
    @PutMapping("/post/{id}")
    public ResponseEntity<Void> updatePost(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                           @PathVariable("id") Long postId,
                                           @RequestBody @Valid UpdatePostRequestDto updatePostRequestDto){
        Post post = postService.updatePost(postId,updatePostRequestDto,member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "게시글 삭제 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글의 경우")
    })
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long postId,
                                           @Parameter(hidden = true) @AuthenticationPrincipal Member member){
        postService.deletePost(postId, member);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 검색 및 전체 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 검색 및 전체 조회 성공 "),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글의 경우")
    })
    @GetMapping("post/search")
    public ResponseEntity<MultiResponseDto> searchPostsPage(PostSearchCondition condition, Pageable pageable){
        Page<PostSearchDto> searchPosts = postService.getSearchPosts(condition,pageable);
        List<PostSearchDto> posts = searchPosts.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(posts,searchPosts),HttpStatus.OK);
    }
}
