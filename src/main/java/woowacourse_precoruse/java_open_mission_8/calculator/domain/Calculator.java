package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.)\\n(.*)");
    private static final String DEFAULT_DELIMITER_REGEX = "[,:]";

    public int calculate(String input) {
        if (input == null || input.isBlank()) {
            return 0;
        }

        return sum(parse(input));
    }

    private String[] parse(String input) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(input);
        if (matcher.find()) {
            String customDelimiter = Pattern.quote(matcher.group(1));
            return matcher.group(2).split(customDelimiter);
        }
        return input.split(DEFAULT_DELIMITER_REGEX);
    }

    private int sum(String[] numbers) {
        return Arrays.stream(numbers)
                .map(this::parseInt)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int parseInt(String number) {
        try {
            int value = Integer.parseInt(number);
            if (value < 0) {
                throw new IllegalArgumentException("음수는 입력할 수 없습니다: " + value);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바르지 않은 입력 형식입니다: " + number);
        }
    }
}
