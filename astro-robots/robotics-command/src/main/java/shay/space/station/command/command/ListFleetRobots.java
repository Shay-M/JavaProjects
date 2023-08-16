package shay.space.station.command.command;

import org.springframework.stereotype.Component;
import shay.space.station.command.infra.RobotCommand;
import shay.space.station.command.utils.MenuBuilder;
import shay.space.station.core.annotations._Command;
import shay.space.station.fleet.SpaceRobotsFleet;
import shay.space.station.infra.base.Robot;
import shay.space.station.infra.behavior.IRobotState;
import shay.space.station.ui.infra.Output;


@_Command
@Component
public class ListFleetRobots implements RobotCommand {
    private static final String LIST_FLEET_ROBOTS = "List fleet robots";
    private static final String SPEC_HEADER = "Robot Specification";
    private static final String TOOLS_HEADER = "List Of Tools:";
    private static final String ROBOT_STATES = "Robot States: ";

    private final SpaceRobotsFleet m_fleet;
    private final Output m_output;
    private final MenuBuilder m_menuBuilder;


    // private final List<Robot> m_robots;
    public ListFleetRobots(final Output output, final SpaceRobotsFleet fleet, final MenuBuilder menuBuilder) {
        m_fleet = fleet;
        m_output = output;
        m_menuBuilder = menuBuilder;
    }


    @Override
    public void execute() {
        for (var robot : m_fleet.getAllRobots()) {
            displayRobotInfo(robot);
        }

    }

    private void displayRobotInfo(final Robot robot) {
        m_output.print(SPEC_HEADER);
        m_output.print("~~~~~~~~~~~~~~~~~~~");
        m_output.print(robot.getData().toString());
        displayRobotStates(robot);
        displayRobotTools(robot);
    }

    private void displayRobotStates(final Robot robot) {
        final IRobotState states = robot.getStates();
        m_output.print(ROBOT_STATES + states.toString());
    }

    private void displayRobotTools(final Robot robot) {
        m_output.print(TOOLS_HEADER);
        m_output.print(m_menuBuilder.Build(robot.getTools()).toString());
    }


    @Override
    public String toString() {
        return LIST_FLEET_ROBOTS;
    }


}
