package teamdropin.server.domain.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.box.entity.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box,Long> {
}
