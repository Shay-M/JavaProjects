package shay.space.station.fleet;

import org.springframework.stereotype.Service;
import shay.space.station.fleet.exception.DuplicateRobotException;
import shay.space.station.fleet.infra.RobotsFleet;
import shay.space.station.infra.base.Robot;
import shay.space.station.ui.infra.Output;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpaceRobotsFleet implements RobotsFleet {
    private static final String DUPLICATE_ROBOT_CALL_SIGN = "Duplicate Robot CallSign: ";
    private static final String NEW_ROBOT_ADDED = "New Robot Added: ";
    private static final String ROBOT_DELETED = "Robot Deleted: ";
    private static final String ROBOT_NOT_FOUND = "Robot Not Found: ";
    private static final String SELECTED_ROBOT = "Selected Robot: ";

    private final Map<String, Robot> m_robotMap = new HashMap<>();
    private final Output m_output;

    public SpaceRobotsFleet(Output output) {
        m_output = output;
    }

    @Override
    public List<Robot> getAllRobots() {
        return m_robotMap.values().stream().toList();
    }

    @Override
    public void addRobot(final Robot newRobot) {
        String callSign = newRobot.getData().callSign();
        if (m_robotMap.containsKey(callSign)) {
            throw new DuplicateRobotException(DUPLICATE_ROBOT_CALL_SIGN + callSign);
        }
        else {
            m_robotMap.put(callSign, newRobot);
            m_output.print(NEW_ROBOT_ADDED + newRobot.getData());
        }
    }

    @Override
    public void deleteRobot(final String callSignToRemove) {
        if (m_robotMap.remove(callSignToRemove) != null) {
            m_output.print(ROBOT_DELETED + callSignToRemove);
        }
        else {
            m_output.print(ROBOT_NOT_FOUND + callSignToRemove);
        }
    }

    @Override
    public Optional<Robot> selectRobot(final String callSign) {
        final Robot selectedRobot = m_robotMap.get(callSign);
        if (selectedRobot != null) {
            m_output.print(SELECTED_ROBOT + selectedRobot.getData());
            return Optional.of(selectedRobot);
        }
        else {
            m_output.print(ROBOT_NOT_FOUND + callSign);
            return Optional.empty();
        }
    }
}
