package teamdropin.server.domain.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.box.entity.Box;

import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository<Box,Long> {

    List<Box> findByMemberId(Long memberId);

    @Modifying
    @Query("update Box b set b.member.id = :newMemberId where b.member.id = :oldMemberId")
    int updateMemberIdForBoxes(@Param("oldMemberId") Long oldMemberId, @Param("newMemberId") Long newMemberId);
}
