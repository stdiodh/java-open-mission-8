package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

@Document(collection = "lottos")
public class Lotto {
    private static final int LOTTO_NUMBER_COUNT = 6;

    @Id
    private String id;
    private final String purchaseId;
    private final List<LottoNumber> numbers;

    protected Lotto() {
        this.numbers = List.of();
        this.purchaseId = null;
    }

    @PersistenceCreator
    public Lotto(String id, String purchaseId, List<LottoNumber> numbers) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.numbers = numbers;
    }

    public Lotto(List<LottoNumber> numbers, String purchaseId) {
        validate(numbers);

        this.id = UUID.randomUUID().toString();
        this.purchaseId = purchaseId;
        this.numbers = numbers.stream()
                .sorted()
                .toList();
    }

    private void validate(List<LottoNumber> numbers) {
        validateSize(numbers);
        validateDuplicate(numbers);
    }

    private void validateSize(List<LottoNumber> numbers) {
        if (numbers == null || numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new BusinessLogicException(ErrorCode.LOTTO_SIZE_INVALID);
        }
    }

    private void validateDuplicate(List<LottoNumber> numbers) {
        long uniqueCount = numbers.stream().distinct().count();
        if (uniqueCount != LOTTO_NUMBER_COUNT) {
            throw new BusinessLogicException(ErrorCode.LOTTO_NUMBER_DUPLICATED);
        }
    }

    public boolean contains(LottoNumber number) {
        return this.numbers.contains(number);
    }

    public int countMatchingNumbers(Lotto other) {
        return (int) this.numbers.stream()
                .filter(other::contains)
                .count();
    }

    public List<LottoNumber> getNumbers() {
        return numbers;
    }

    public String getPurchaseId() {
        return purchaseId;
    }
}
