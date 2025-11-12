package woowacourse_precoruse.java_open_mission_8.lotto.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lottos")
public class Lotto {
    private static final int LOTTO_NUMBER_COUNT = 6;

    @Id
    private String id;

    private final List<LottoNumber> numbers;

    public Lotto(List<LottoNumber> numbers) {
        validate(numbers);

        this.id = UUID.randomUUID().toString();
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
            throw new IllegalArgumentException("[ERROR] 로또 번호는 " + LOTTO_NUMBER_COUNT + "개여야 합니다.");
        }
    }

    private void validateDuplicate(List<LottoNumber> numbers) {
        long uniqueCount = numbers.stream().distinct().count();
        if (uniqueCount != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
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
}
