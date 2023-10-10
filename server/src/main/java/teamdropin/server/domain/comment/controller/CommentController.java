package teamdropin.server.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.comment.dto.CreateCommentRequestDto;
import teamdropin.server.domain.comment.dto.UpdateCommentRequestDto;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.comment.mapper.CommentMapper;
import teamdropin.server.domain.comment.service.CommentService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.util.UriCreator;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final static String COMMENT_DEFAULT_URL = "/api/post/";

    private final CommentService commentService;

    @Operation(summary = "댓글 등록 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 등록 유효성 검증에 실패한 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글의 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우")
    })
    @PostMapping("/post/{id}/comment")
    public ResponseEntity<Void> createComment(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                              @PathVariable("id") Long postId,
                                              @RequestBody @Valid CreateCommentRequestDto createCommentRequestDto){
        Long commentId = commentService.createComment(createCommentRequestDto, member, postId);
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URL + postId.toString() + "/comment", commentId);
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "댓글 수정 API" , description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 등록 유효성 검증에 실패한 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글의 경우 <br> 존재하지 않는 게시글의 경우")
    })
    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Void> updateComment(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                              @PathVariable("postId") Long postId,
                                              @PathVariable("commentId") Long commentId,
                                              @RequestBody @Valid UpdateCommentRequestDto updateCommentRequestDto){
        commentService.updateComment(postId, commentId, updateCommentRequestDto, member);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 댓글의 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글의 경우 <br> 존재하지 않는 게시글의 경우")
    })
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                              @PathVariable("postId") Long postId,
                                              @PathVariable("commentId") Long commentId){
        commentService.deleteComment(postId, commentId, member);
        return ResponseEntity.noContent().build();
    }
}
