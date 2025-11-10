package woowacourse_precoruse.java_open_mission_8.racingcar.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.CarDto;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarRequest;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarResponse;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;

class RacingCarServiceTest {

    @Test
    @DisplayName("게임을 진행하고 각 라운드 결과와 최종 우승자를 반환한다")
    void play_게임을_정상적으로_완료한다() {
        RacingCarRequest request = new RacingCarRequest("pobi,woni", 2);

        // Round 1: pobi(5:전진), woni(3:정지) -> pobi:1, woni:0
        // Round 2: pobi(6:전진), woni(7:전진) -> pobi:2, woni:1
        Iterator<Integer> fixedNumbers = Arrays.asList(5, 3, 6, 7).iterator();
        IntSupplier mockNumberProvider = fixedNumbers::next;

        RacingCarService racingCarService = new RacingCarService(mockNumberProvider);

        RacingCarResponse response = racingCarService.play(request);

        // 1. 총 라운드 수 검증
        assertThat(response.rounds()).hasSize(2);

        // 2. 각 라운드 별 상태 검증
        List<CarDto> round1 = response.rounds().get(0);
        assertThat(round1).extracting("name").containsExactly("pobi", "woni");
        assertThat(round1).extracting("position").containsExactly(1, 0);

        List<CarDto> round2 = response.rounds().get(1);
        assertThat(round2).extracting("name").containsExactly("pobi", "woni");
        assertThat(round2).extracting("position").containsExactly(2, 1);

        // 3. 최종 우승자 검증 (pobi가 위치 2로 단독 우승)
        assertThat(response.winners()).containsExactly("pobi");
    }

    @Test
    @DisplayName("모두가 전진하지 못하면 공동 우승자가 된다")
    void play_모두_정지하면_모두_우승자다() {
        RacingCarRequest request = new RacingCarRequest("pobi,woni", 3);
        RacingCarService racingCarService = new RacingCarService(() -> 3);
        RacingCarResponse response = racingCarService.play(request);

        // 모든 라운드에서 위치가 0이어야 한다.
        assertThat(response.rounds().get(2))
                .extracting("position")
                .containsExactly(0, 0);

        // 모두가 공동 우승자여야 한다.
        assertThat(response.winners()).containsExactly("pobi", "woni");
    }
}
