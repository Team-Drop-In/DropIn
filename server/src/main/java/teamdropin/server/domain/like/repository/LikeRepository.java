package teamdropin.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.entity.LikeCategory;
import teamdropin.server.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberIdAndPostId(Long memberId, Long postId);
    Optional<Like> findByMemberIdAndCommentId(Long memberId, Long commentId);
    Optional<Like> findByMemberIdAndBoxId(Long memberId, Long boxId);

    Optional<Like> findByMemberIdAndReviewId(Long memberId, Long reviewId);

    List<Like> findByMemberIdAndLikeCategory(Long memberId, LikeCategory likeCategory);

    @Modifying
    @Query("delete from Like l where l.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);

    @Modifying
    @Query("delete from Like l where l.comment in :comments")
    void deleteAllByComments(@Param("comments") List<Comment> comments);

    @Modifying
    @Query("delete from Like l where l.comment.id = :commentId")
    void deleteAllByCommentId(@Param("commentId") Long commentId);

    @Modifying
    @Query("delete from Like l where l.review in :reviews")
    void deleteAllByReviews(@Param("reviews")List<Review> reviews);

    @Modifying
    @Query("delete from Like l where l.review.id = :reviewId")
    void deleteAllByReviewId(@Param("reviewId") Long reviewId);

    @Modifying
    @Query("delete from Like l where l.box.id = :boxId")
    void deleteAllByBoxId(@Param("boxId") Long boxId);

}
