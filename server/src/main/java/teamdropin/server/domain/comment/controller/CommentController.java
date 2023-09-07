package teamdropin.server.domain.comment.controller;

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
    private final CommentMapper commentMapper;

    @PostMapping("/post/{id}/comment")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal Member member,
                                              @PathVariable("id") Long postId,
                                              @RequestBody @Valid CreateCommentRequestDto createCommentRequestDto){
        Comment comment = commentMapper.toEntity(createCommentRequestDto);
        Long commentId = commentService.createComment(comment, member, postId);
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URL + postId.toString() + "/comment", commentId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal Member member,
                                              @PathVariable("postId") Long postId,
                                              @PathVariable("commentId") Long commentId,
                                              @RequestBody @Valid UpdateCommentRequestDto updateCommentRequestDto){
        commentService.updateComment(postId, commentId, updateCommentRequestDto, member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal Member member,
                                              @PathVariable("postId") Long postId,
                                              @PathVariable("commentId") Long commentId){
        commentService.deleteComment(postId, commentId, member);
        return ResponseEntity.noContent().build();
    }
}
