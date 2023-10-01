package teamdropin.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.entity.LikeCategory;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberIdAndPostId(Long memberId, Long postId);
    Optional<Like> findByMemberIdAndCommentId(Long memberId, Long commentId);
    Optional<Like> findByMemberIdAndBoxId(Long memberId, Long boxId);

    List<Like> findByMemberIdAndLikeCategory(Long memberId, LikeCategory likeCategory);

    void deleteAllByBoxId(Long boxId);
}
