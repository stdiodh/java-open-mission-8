package woowacourse_precoruse.java_open_mission_8.lotto.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;

import static org.assertj.core.api.Assertions.assertThat;

class RandomLottoNumberGeneratorTest {
    @Test
    void 로또번호를_자동으로_생성한다() {
        LottoNumberGenerator generator = new RandomLottoNumberGenerator();
        String purchaseId = UUID.randomUUID().toString();

        Lotto lotto = generator.generate(purchaseId);

        assertThat(lotto).isNotNull();
        assertThat(lotto.getNumbers()).hasSize(6);
        assertThat(lotto.getPurchaseId()).isEqualTo(purchaseId);

        long uniqueCount = lotto.getNumbers().stream().distinct().count();
        assertThat(uniqueCount).isEqualTo(6);
    }
}
