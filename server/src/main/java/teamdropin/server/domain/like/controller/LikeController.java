package teamdropin.server.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.like.dto.LikeRequestDto;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.entity.Member;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
     * 게시글 좋아요
     */
    @PostMapping("/post/like")
    public ResponseEntity<Void> postLike(@AuthenticationPrincipal Member member,
                                         @RequestBody LikeRequestDto likeRequestDto){
        likeService.like(member, likeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 좋아요
     */
    @PostMapping("/comment/like")
    public ResponseEntity<Void> commentLike(@AuthenticationPrincipal Member member,
                                            @RequestBody LikeRequestDto likeRequestDto){
        likeService.like(member, likeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/box/like")
    public ResponseEntity<Void> boxLike(@AuthenticationPrincipal Member member,
                                            @RequestBody LikeRequestDto likeRequestDto){
        likeService.like(member, likeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
