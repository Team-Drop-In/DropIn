package teamdropin.server.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.review.dto.CreateReviewRequestDto;
import teamdropin.server.domain.review.dto.UpdateReviewRequestDto;
import teamdropin.server.domain.review.service.ReviewService;
import teamdropin.server.global.util.UriCreator;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final static String REVIEW_DEFAULT_URL = "/api/box/";

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "리뷰 등록 성공"),
            @ApiResponse(responseCode = "400", description = "리뷰 등록 유효성 검증에 실패한 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 박스인 경우")
    })
    @PostMapping("/box/{boxId}/review")
    public ResponseEntity<Void> createReview(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                             @PathVariable("boxId") Long boxId,
                                             @RequestBody @Valid CreateReviewRequestDto createReviewRequestDto){

        Long reviewId = reviewService.createReview(createReviewRequestDto, member, boxId);
        URI location = UriCreator.createUri(REVIEW_DEFAULT_URL + boxId.toString() + "/review", reviewId);
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "리뷰 수정 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공"),
            @ApiResponse(responseCode = "400", description = "리뷰 수정 유효성 검증에 실패한 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 박스인 경우 <br> 존재하지 않는 리뷰인 경우"),
    })
    @PutMapping("/box/{boxId}/review/{reviewId}")
    public ResponseEntity<Void> updateReview(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                             @PathVariable("boxId") Long boxId,
                                             @PathVariable("reviewId") Long reviewId,
                                             @RequestBody @Valid UpdateReviewRequestDto updateReviewRequestDto){
        reviewService.updateReview(updateReviewRequestDto, boxId, reviewId, member);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "리뷰 삭제 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "리뷰 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 박스인 경우 <br> 존재하지 않는 리뷰인 경우")
    })
    @DeleteMapping("/box/{boxId}/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                             @PathVariable("boxId") Long boxId,
                                             @PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(boxId,reviewId,member);
        return ResponseEntity.noContent().build();
    }
}
