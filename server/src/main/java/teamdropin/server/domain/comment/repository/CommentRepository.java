package teamdropin.server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.comment.entity.Comment;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByPostIdAndId(Long postId, Long id);

    Optional<Comment> findByPostIdAndMemberIdAndId(Long postId, Long memberId, Long id);
}
