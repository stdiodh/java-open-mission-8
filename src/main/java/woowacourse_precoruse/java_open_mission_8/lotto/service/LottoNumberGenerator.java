package woowacourse_precoruse.java_open_mission_8.lotto.service;

import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;

public interface LottoNumberGenerator {
    Lotto generate(String purchaseId);
}
