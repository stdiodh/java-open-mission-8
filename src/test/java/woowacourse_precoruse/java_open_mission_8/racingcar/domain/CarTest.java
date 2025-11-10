package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import org.junit.jupiter.api.Test;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.CarDto;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {
    private final Name testName = new Name("test");

    @Test
    void 유효한_이름으로_생성시_위치는_0이다() {
        Car car = new Car(testName);

        // Name 객체는 생성 시점에서 유효성이 검증되므로, position만 확인
        assertThat(car.getPosition().getValue()).isZero();
    }

    @Test
    void move_입력값이_4이상이면_위치를_1증가시킨다() {
        Car car = new Car(testName);
        int initialPosition = car.getPosition().getValue(); // 0

        // 4 이상 (전진 조건 만족)
        car.move(4);
        car.move(9);

        // 2번 전진했으므로 위치는 2가 된다.
        assertThat(car.getPosition().getValue()).isEqualTo(initialPosition + 2);
    }

    @Test
    void move_입력값이_3이하이면_위치를_증가시키지_않는다() {
        Car car = new Car(testName);
        int initialPosition = car.getPosition().getValue(); // 0

        // 3 이하 (전진 조건 불만족)
        car.move(0);
        car.move(3);

        // 전진이 없었으므로 위치는 0을 유지해야 한다.
        assertThat(car.getPosition().getValue()).isEqualTo(initialPosition);
    }

    @Test
    void toDto_호출시_현재_상태를_담은_CarDto를_반환한다() {
        Car car = new Car(testName);
        car.move(5); // 1번 전진

        CarDto dto = car.toDto();

        // DTO 필드 값이 Car의 현재 상태와 일치하는지 확인
        assertThat(dto.name()).isEqualTo("test");
        assertThat(dto.position()).isEqualTo(1);
    }

    @Test
    void isAheadOf_으로_상대보다_앞서있는지_비교한다() {
        Car car = new Car(testName);
        car.move(5); // 위치 1

        Position otherPosition = new Position(); // 위치 0

        // 현재 Car의 위치(1)가 다른 위치(0)보다 앞선다.
        assertThat(car.isAheadOf(otherPosition)).isTrue();
    }

    @Test
    void isAtPosition_으로_상대와_같은_위치인지_비교한다() {
        Car car = new Car(testName);
        car.move(5); // 위치 1

        Position samePosition = new Position();
        samePosition.increase(); // 위치 1

        // 현재 Car의 위치(1)가 다른 위치(1)와 같다.
        assertThat(car.isAtPosition(samePosition)).isTrue();
    }
}