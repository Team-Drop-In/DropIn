package teamdropin.server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByPostIdAndId(Long postId, Long id);

    Optional<Comment> findByPostIdAndMemberIdAndId(Long postId, Long memberId, Long id);

    long countByMemberId(Long memberId);

    @Modifying
    @Query("delete from Comment c where c.post.id = :postId")
    void deleteAllCommentsByPostId(@Param("postId") Long postId);

    List<Comment> findAllCommentsByPostId(Long postId);

    @Modifying
    @Query("update Comment c set c.member.id = :newMemberId where c.member.id = :oldMemberId")
    int updateMemberIdForComments(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);


}
