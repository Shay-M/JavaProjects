package shay.space.station.fleet.infra;

import shay.space.station.infra.base.Robot;

import java.util.List;
import java.util.Optional;

public interface RobotsFleet {

    List<Robot> getAllRobots();

    void addRobot(final Robot robot);

    void deleteRobot(final String callSignToRemove);

    Optional<Robot> selectRobot(final String callSign);

}
