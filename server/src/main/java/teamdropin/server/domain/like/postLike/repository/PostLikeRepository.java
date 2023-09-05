package teamdropin.server.domain.like.postLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByMemberAndPost(Member member, Post post);
    Integer countByPostId(Long id);
}
