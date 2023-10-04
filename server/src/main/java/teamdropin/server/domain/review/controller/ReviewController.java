package teamdropin.server.domain.review.controller;

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

    @PostMapping("/box/{boxId}/review")
    public ResponseEntity<Void> createReview(@AuthenticationPrincipal Member member,
                                             @PathVariable("boxId") Long boxId,
                                             @RequestBody @Valid CreateReviewRequestDto createReviewRequestDto){

        Long reviewId = reviewService.createReview(createReviewRequestDto, member, boxId);
        URI location = UriCreator.createUri(REVIEW_DEFAULT_URL + boxId.toString() + "/review", reviewId);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/box/{boxId}/review/{reviewId}")
    public ResponseEntity<Void> updateReview(@AuthenticationPrincipal Member member,
                                             @PathVariable("boxId") Long boxId,
                                             @PathVariable("reviewId") Long reviewId,
                                             @RequestBody @Valid UpdateReviewRequestDto updateReviewRequestDto){
        reviewService.updateReview(updateReviewRequestDto, boxId, reviewId, member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/box/{boxId}/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@AuthenticationPrincipal Member member,
                                             @PathVariable("boxId") Long boxId,
                                             @PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(boxId,reviewId,member);
        return ResponseEntity.noContent().build();
    }
}
