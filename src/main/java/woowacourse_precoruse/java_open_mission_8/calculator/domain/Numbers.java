package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import java.util.ArrayList;
import java.util.List;

public class Numbers {
    private final List<Integer> values;

    public Numbers(List<String> textTokens) {
        this.values = toInts(textTokens);
        validateNoNegative();
    }

    private List<Integer> toInts(List<String> textTokens) {
        List<Integer> numbers = new ArrayList<>();
        try {
            for (String token : textTokens) {
                numbers.add(Integer.parseInt(token));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자가 아닌 값이 포함되어 있습니다.");
        }
        return numbers;
    }

    private void validateNoNegative() {
        for (int number : values) {
            if (number < 0) {
                throw new IllegalArgumentException("[ERROR] 음수는 포함될 수 없습니다: " + number);
            }
        }
    }

    public int sum() {
        return values.stream().mapToInt(Integer::intValue).sum();
    }
}
