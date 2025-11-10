package woowacourse_precoruse.java_open_mission_8.racingcar.dto;

import java.util.List;

public record RacingCarResponse(List<List<CarDto>> rounds, List<String> winners) {
}
