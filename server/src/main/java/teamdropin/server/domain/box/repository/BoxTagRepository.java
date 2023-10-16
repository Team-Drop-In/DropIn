package teamdropin.server.domain.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import teamdropin.server.domain.box.entity.BoxTag;

public interface BoxTagRepository extends JpaRepository<BoxTag, Long> {

    @Modifying
    @Query("delete from BoxTag b where b.box.id = :boxId")
    void deleteAllByBoxId(Long boxId);

}
