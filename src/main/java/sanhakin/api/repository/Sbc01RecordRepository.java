package sanhakin.api.repository;

import sanhakin.api.entity.Sbc01RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface Sbc01RecordRepository extends JpaRepository<Sbc01RecordEntity, String> {
    Optional<Sbc01RecordEntity> findByBizrNo(String bizrNo);
    List<Sbc01RecordEntity> findByBizrNoIn(List<String> bizrNos);
}