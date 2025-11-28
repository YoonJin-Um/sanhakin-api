package sanhakin.api.repository;

import sanhakin.api.entity.Sbc01RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Sbc01RecordRepository extends JpaRepository<Sbc01RecordEntity, Long> {
    Optional<Sbc01RecordEntity> findByBizrNo(String bizrNo);
}
