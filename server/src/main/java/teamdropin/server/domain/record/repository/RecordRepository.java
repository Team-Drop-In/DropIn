package teamdropin.server.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.record.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
