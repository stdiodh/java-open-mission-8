package woowacourse_precoruse.java_open_mission_8.lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.Lotto;
import woowacourse_precoruse.java_open_mission_8.lotto.domain.LottoNumber;

@Component
public class RandomLottoNumberGenerator implements LottoNumberGenerator {
    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    @Override
    public Lotto generate(String purchaseId) {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                MIN_LOTTO_NUMBER,
                MAX_LOTTO_NUMBER,
                LOTTO_NUMBER_COUNT
        );

        List<LottoNumber> lottoNumbers = numbers.stream()
                .map(LottoNumber::new)
                .collect(Collectors.toList());

        return new Lotto(lottoNumbers, purchaseId);
    }
}
