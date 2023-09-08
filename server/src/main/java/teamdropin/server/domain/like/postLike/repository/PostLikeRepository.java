package teamdropin.server.domain.like.postLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByMemberIdAndPostId(Long memberId, Long postId);
    Integer countByPostId(Long id);

    List<PostLike> findByPostId(Long postId);
}
