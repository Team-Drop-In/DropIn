package teamdropin.server.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.review.entity.Review;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByBoxIdAndId(Long boxId, Long reviewId);

    Optional<Review> findByBoxIdAndMemberIdAndId(Long boxId, Long memberId, Long reviewId);

}
