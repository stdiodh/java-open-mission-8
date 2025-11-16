package woowacourse_precoruse.java_open_mission_8.calculator.domain;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;
import woowacourse_precoruse.java_open_mission_8.common.exception.ErrorCode;

public class Parser {
    private static final String CUSTOM_SEPARATOR_START = "//";
    private static final String CUSTOM_SEPARATOR_END = "\n";
    private static final String DEFAULT_DELIMITER_REGEX = "[,:]";

    public List<String> parse(String input) {
        if (input == null || input.isBlank()) {
            return List.of();
        }

        if (input.startsWith(CUSTOM_SEPARATOR_START) && input.contains(CUSTOM_SEPARATOR_END)) {
            return splitByCustomSeparator(input);
        }

        return splitByDefaultSeparator(input);
    }

    private String extractCustomSeparator(String text) {
        int startIndex = CUSTOM_SEPARATOR_START.length();
        int endIndex = text.indexOf(CUSTOM_SEPARATOR_END);

        String separator = text.substring(startIndex, endIndex);

        if (separator.isEmpty()) {
            throw new BusinessLogicException(ErrorCode.CALC_INVALID_CUSTOM_DELIMITER);
        }

        return separator;
    }

    private String extractTargetText(String text) {
        int endIndex = text.indexOf(CUSTOM_SEPARATOR_END);

        return text.substring(endIndex + CUSTOM_SEPARATOR_END.length());
    }

    private List<String> splitByCustomSeparator(String text) {
        String customSeparator = extractCustomSeparator(text);
        String textToSplit = extractTargetText(text);

        return Arrays.asList(textToSplit.split(Pattern.quote(customSeparator)));
    }

    private List<String> splitByDefaultSeparator(String text) {
        return Arrays.asList(text.split(DEFAULT_DELIMITER_REGEX));
    }
}
