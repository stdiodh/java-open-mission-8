package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public class WinningLotto {
    private final Lotto winningNumbers;
    private final LottoNumber bonusNumber;

    public WinningLotto(Lotto winningNumbers, LottoNumber bonusNumber) {
        validateBonusNumber(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;;
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusNumber(Lotto winningNumbers, LottoNumber bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new BusinessLogicException(ErrorCode.LOTTO_NUMBER_DUPLICATED);
        }
    }

    public Rank match(Lotto userLotto) {
        int matchCount = winningNumbers.countMatchingNumbers(userLotto);
        boolean bonusMatch = userLotto.contains(bonusNumber);

        return Rank.valueOfRank(matchCount, bonusMatch);
    }

    public Lotto getWinningNumbers() {
        return winningNumbers;
    }

    public LottoNumber getBonusNumber() {
        return bonusNumber;
    }
}
