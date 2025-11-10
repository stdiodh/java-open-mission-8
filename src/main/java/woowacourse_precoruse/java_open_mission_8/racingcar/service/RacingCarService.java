package woowacourse_precoruse.java_open_mission_8.racingcar.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;
import org.springframework.stereotype.Service;
import woowacourse_precoruse.java_open_mission_8.racingcar.domain.Cars;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.CarDto;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarRequest;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.RacingCarResponse;

@Service
public class RacingCarService {
    private final IntSupplier numberProvider;

    public RacingCarService() {
        this(() -> Randoms.pickNumberInRange(0, 9));
    }

    public RacingCarService(IntSupplier numberProvider) {
        this.numberProvider = numberProvider;
    }

    public RacingCarResponse play(RacingCarRequest request) {
        Cars cars = Cars.from(request.names());
        int tryCount = request.count();

        List<List<CarDto>> rounds = new ArrayList<>();
        for (int i = 0; i < tryCount; i++) {
            cars.playRound(numberProvider);
            rounds.add(cars.toDtos());
        }

        return new RacingCarResponse(rounds, cars.findWinners());
    }
}
