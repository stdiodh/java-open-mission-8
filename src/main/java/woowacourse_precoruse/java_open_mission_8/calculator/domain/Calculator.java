package woowacourse_precoruse.java_open_mission_8.calculator.domain;

public class Calculator {
    public int calculate(Numbers numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("[ERROR] 숫자들 안에 null이 포함되어 있습니다.");
        }
        return numbers.sum();
    }
}
