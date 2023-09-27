package teamdropin.server.domain.boxTag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamdropin.server.domain.boxTag.entity.BoxTag;

public interface BoxTagRepository extends JpaRepository<BoxTag, Long> {

    void deleteAllByBoxId(Long id);

}
