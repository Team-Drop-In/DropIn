package teamdropin.server.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "게시글 좋아요 및 취소 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 및 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글의 경우 <br> 존재하지 않는 좋아요인 경우")
    })
    @PostMapping("/post/like")
    public ResponseEntity<Void> postLike(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                         @RequestBody LikeRequestDto likeRequestDto){
        likeService.like(member, likeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "댓글 좋아요 및 취소 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 좋아요 및 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 댓글의 경우 <br> 존재하지 않는 좋아요인 경우")
    })
    @PostMapping("/comment/like")
    public ResponseEntity<Void> commentLike(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                            @RequestBody LikeRequestDto likeRequestDto){
        likeService.like(member, likeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "박스 좋아요 및 취소 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "박스 좋아요 및 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 박스의 경우 <br> 존재하지 않는 좋아요인 경우")
    })
    @PostMapping("/box/like")
    public ResponseEntity<Void> boxLike(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                        @RequestBody LikeRequestDto likeRequestDto){
        likeService.like(member, likeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "리뷰 좋아요 및 취소 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 좋아요 및 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리뷰의 경우 <br> 존재하지 않는 좋아요인 경우")
    })
    @PostMapping("/review/like")
    public ResponseEntity<Void> reviewLike(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                        @RequestBody LikeRequestDto likeRequestDto){
        likeService.like(member, likeRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
