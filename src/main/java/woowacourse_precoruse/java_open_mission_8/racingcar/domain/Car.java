package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import woowacourse_precoruse.java_open_mission_8.racingcar.dto.CarDto;

public class Car {
    private final Name name;
    private final Position position;
    private static final int MOVING_FORWARD = 4;

    public Car(Name name) {
        this.name = name;
        this.position = new Position();
    }

    public String getNameValue() {
        return name.value();
    }

    public Position getPosition() {
        return position;
    }

    public void move(int number) {
        if (number >= MOVING_FORWARD) {
            position.increase();
        }
    }

    public CarDto toDto() {
        return new CarDto(name.value(), position.getValue());
    }

    public boolean isAheadOf(Position otherPosition) {
        return this.position.isGreaterThan(otherPosition);
    }

    public boolean isAtPosition(Position otherPosition) {
        return this.position.isSameAs(otherPosition);
    }
}
