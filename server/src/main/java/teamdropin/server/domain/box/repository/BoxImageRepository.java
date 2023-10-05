package teamdropin.server.domain.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.box.entity.BoxImage;

import java.util.Optional;

@Repository
public interface BoxImageRepository extends JpaRepository<BoxImage, Long> {
    Optional<BoxImage> findByImageIndex(int imageIndex);

    @Modifying
    @Query("delete from BoxImage b where b.box.id = :boxId")
    void deleteAllByBoxId(@Param("boxId") Long boxId);
}
