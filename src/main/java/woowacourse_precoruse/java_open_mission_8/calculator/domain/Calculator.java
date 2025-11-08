package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import java.util.Arrays;

public class Calculator {
    private static final String DEFAULT_DELIMITER_REGEX = "[,:]";

    public int calculate(String input) {
        if (input == null || input.isBlank()) {
            return 0;
        }

        return Arrays.stream(input.split(DEFAULT_DELIMITER_REGEX))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
