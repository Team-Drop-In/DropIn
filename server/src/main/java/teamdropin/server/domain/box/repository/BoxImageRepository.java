package teamdropin.server.domain.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.box.boxImage.BoxImage;

import java.util.Optional;

@Repository
public interface BoxImageRepository extends JpaRepository<BoxImage, Long> {
    Optional<BoxImage> findByImageIndex(int imageIndex);
}
