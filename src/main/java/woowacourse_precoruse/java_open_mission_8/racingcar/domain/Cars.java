package woowacourse_precoruse.java_open_mission_8.racingcar.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntSupplier;
import woowacourse_precoruse.java_open_mission_8.racingcar.dto.CarDto;

public class Cars {
    private static final int MIN_CARS_COUNT = 2;

    private final List<Car> cars;

    public Cars(List<Car> cars) {
        validate(cars);
        this.cars = cars;
    }

    private void validate(List<Car> cars) {
        validateSize(cars);
        validateUniqueness(cars);
    }

    private void validateSize(List<Car> cars) {
        if (cars.size() < MIN_CARS_COUNT) {
            throw new IllegalArgumentException("[ERROR] 자동차는 2대 이상 참여해야 합니다.");
        }
    }

    private void validateUniqueness(List<Car> cars) {
        Set<String> names = new HashSet<>();
        for (Car car : cars) {
            if (!names.add(car.getNameValue())) {
                throw new IllegalArgumentException("[ERROR] 자동차 이름은 중복될 수 없습니다.");
            }
        }
    }

    public void playRound(IntSupplier numberProvider) {
        for (Car car : cars) {
            car.move(numberProvider.getAsInt());
        }
    }

    public List<CarDto> toDtos() {
        return cars.stream()
                .map(Car::toDto)
                .toList();
    }

    public static Cars from(String names) {
        List<Car> carList = Arrays.stream(names.split(","))
                .map(String::trim)
                .map(Name::new)
                .map(Car::new)
                .toList();
        return new Cars(carList);
    }

    public List<String> findWinners() {
        Position maxPosition = findMaxPosition();
        return cars.stream()
                .filter(car -> car.isAtPosition(maxPosition))
                .map(Car::getNameValue)
                .toList();
    }

    private Position findMaxPosition() {
        Position maxPosition = cars.getFirst().getPosition();
        for (Car car : cars) {
            if (car.isAheadOf(maxPosition)) {
                maxPosition = car.getPosition();
            }
        }
        return maxPosition;
    }
}
