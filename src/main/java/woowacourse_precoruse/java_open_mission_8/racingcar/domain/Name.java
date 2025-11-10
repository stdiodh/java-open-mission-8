package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

public record Name(String value) {
    private static final int MAX_NAME_LENGTH = 5;

    public Name {
        validateNullValue(value);
        validateOverLength(value);
    }

    private void validateNullValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 자동차 이름은 공백일 수 없습니다.");
        }
    }

    private void validateOverLength(String value) {
        if (value.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 자동차 이름은 5자를 초과할 수 없습니다.");
        }
    }
}
