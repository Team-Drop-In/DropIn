package teamdropin.server.domain.like.postLike.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.like.postLike.service.PostLikeService;
import teamdropin.server.domain.member.entity.Member;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/postlike/{postId}")
    public ResponseEntity<Void> postLike(@AuthenticationPrincipal Member member,
                                         @PathVariable Long postId){
        postLikeService.like(member, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
