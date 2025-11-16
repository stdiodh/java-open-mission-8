package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @Test
    void 초기_위치는_0이다() {
        Position position = new Position();
        assertThat(position.getValue()).isEqualTo(0);
    }

    @Test
    void increase_호출시_위치가_1증가한다() {
        Position position = new Position();
        position.increase();
        position.increase();
        assertThat(position.getValue()).isEqualTo(2);
    }

    @Test
    void isGreaterThan_으로_크기를_비교한다() {
        Position greater = new Position();
        greater.increase();
        Position smaller = new Position();

        assertThat(greater.isGreaterThan(smaller)).isTrue();
        assertThat(smaller.isGreaterThan(greater)).isFalse();
    }

    @Test
    void isSameAs_으로_같은_위치인지_비교한다() {
        Position targetPosition = new Position();
        targetPosition.increase();

        Position samePosition = new Position();
        samePosition.increase();

        Position differentPosition = new Position();

        assertThat(targetPosition.isSameAs(samePosition)).isTrue();
        assertThat(targetPosition.isSameAs(differentPosition)).isFalse();
    }
}
