package teamdropin.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamdropin.server.domain.like.entity.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberIdAndPostId(Long memberId, Long postId);
    Optional<Like> findByMemberIdAndCommentId(Long memberId, Long commentId);
    Optional<Like> findByMemberIdAndBoxId(Long memberId, Long boxId);

}
