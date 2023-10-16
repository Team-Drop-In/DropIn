package teamdropin.server.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByBoxIdAndId(Long boxId, Long reviewId);

    Optional<Review> findByBoxIdAndMemberIdAndId(Long boxId, Long memberId, Long reviewId);

    List<Review> findByBoxId(Long boxId);

    @Modifying
    @Query("delete from Review r where r.box.id = :boxId")
    void deleteAllReviewByBoxId(@Param("boxId") Long boxId);
}
