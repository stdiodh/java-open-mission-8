package woowacourse_precoruse.java_open_mission_8.lotto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;

@Repository
public interface LottoRepository extends MongoRepository<Lotto, String> {
}
