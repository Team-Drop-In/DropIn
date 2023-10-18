package teamdropin.server.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.post.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    long countByMemberId(Long memberId);

    @Modifying
    @Query("update Post p set p.member.id = :newMemberId where p.member.id = :oldMemberId")
    int updateMemberIdForPosts(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);

}
