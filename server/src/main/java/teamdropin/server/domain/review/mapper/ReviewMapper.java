package teamdropin.server.domain.review.mapper;

import org.springframework.stereotype.Component;
import teamdropin.server.domain.review.dto.CreateReviewRequestDto;
import teamdropin.server.domain.review.dto.ReviewResponseDto;
import teamdropin.server.domain.review.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {
    public Review toEntity(CreateReviewRequestDto createReviewRequestDto){
        return Review.builder()
                .body(createReviewRequestDto.getBody())
                .build();
    }

    public ReviewResponseDto reviewToReviewResponseDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .nickname(review.getMember().getNickname())
                .body(review.getBody())
                .checkLike(false)
                .checkWriter(false)
                .likeCount(review.getReviewLikes().size())
                .createdAt(review.getCreatedDate())
                .build();
    }

    public List<ReviewResponseDto> reviewsToReviewResponseDtoList(List<Review> reviews){
        List<ReviewResponseDto> resultList =
                reviews.stream().map(this::reviewToReviewResponseDto).collect(Collectors.toList());
        return resultList;
    }
}
