package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.function.IntSupplier;
import woowacourse_precoruse.java_open_mission_8.common.exception.BusinessLogicException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarsTest {

    @Test
    void from_팩토리_메서드로_Cars를_정상적으로_생성한다() {
        String names = "pobi,woni,jun";
        Cars cars = Cars.from(names);

        assertThat(cars.toDtos()).hasSize(3);
        assertThat(cars.toDtos().get(0).name()).isEqualTo("pobi");
    }

    @ParameterizedTest
    @ValueSource(strings = {"pobi,pobi", "a,b,a"})
    void 자동차_이름이_중복되면_생성시_예외가_발생한다(String inputNames) {
        assertThatThrownBy(() -> Cars.from(inputNames))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("자동차 이름은 중복될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"pobi", "a"})
    void 자동차가_2대_미만이면_생성시_예외가_발생한다(String inputNames) {
        assertThatThrownBy(() -> Cars.from(inputNames))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("자동차는 2대 이상 참여해야 합니다.");
    }

    @Test
    void playRound_호출시_전진_조건에_따라_각_자동차의_위치가_변한다() {
        Cars cars = Cars.from("pobi,woni");

        // Mocking: pobi에게는 5(전진), woni에게는 3(멈춤)을 제공하는 IntSupplier
        IntSupplier mockProvider = new IntSupplier() {
            private int count = 0;
            @Override
            public int getAsInt() {
                // 첫 번째 차(pobi): 5, 두 번째 차(woni): 3
                return count++ % 2 == 0 ? 5 : 3;
            }
        };

        cars.playRound(mockProvider);

        // pobi는 1, woni는 0이 되어야 한다.
        assertThat(cars.toDtos().get(0).position()).isEqualTo(1);
        assertThat(cars.toDtos().get(1).position()).isZero();
    }

    @Test
    void findWinners_가장_앞선_위치의_자동차_이름을_반환한다() {
        // given: pobi(위치 1), woni(위치 1), jun(위치 0)
        Cars cars = Cars.from("pobi,woni,jun");

        IntSupplier winnerProvider = () -> 5; // 모두 전진 조건 만족 가정

        // 1. 모든 차를 전진시켜 pobi와 woni를 동점으로 만든다. (jun은 멈춘다)
        cars.playRound(() -> 5); // 1, 1, 1
        cars.playRound(() -> 3); // 1, 1, 1 (멈춤)

        // 2. pobi와 woni만 전진시켜 2점으로 만든다.
        // Mocking: pobi: 5, woni: 5, jun: 3
        IntSupplier customProvider = new IntSupplier() {
            private final int[] values = {5, 5, 3}; // pobi, woni, jun
            private int index = 0;
            @Override
            public int getAsInt() {
                return values[index++ % values.length];
            }
        };
        cars.playRound(customProvider); // pobi: 2, woni: 2, jun: 1

        List<String> winners = cars.findWinners();

        assertThat(winners).containsExactlyInAnyOrder("pobi", "woni");
        assertThat(winners).doesNotContain("jun");
    }

    @Test
    void findWinners_모두_같은_위치일_때_모두_우승자로_반환한다() {
        Cars cars = Cars.from("pobi,woni,jun");
        cars.playRound(() -> 5);
        List<String> winners = cars.findWinners();
        assertThat(winners).containsExactlyInAnyOrder("pobi", "woni", "jun");
    }
}
