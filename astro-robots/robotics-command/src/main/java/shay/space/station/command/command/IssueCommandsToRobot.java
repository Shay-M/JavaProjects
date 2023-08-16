package shay.space.station.command.command;

import org.springframework.stereotype.Component;
import shay.space.station.command.command.exception.InvalidRobotSelectionException;
import shay.space.station.command.infra.RobotCommand;
import shay.space.station.command.utils.MenuBuilder;
import shay.space.station.core.annotations._Command;
import shay.space.station.fleet.SpaceRobotsFleet;
import shay.space.station.infra.base.Robot;
import shay.space.station.robots.factory.RobotFactory;
import shay.space.station.ui.infra.InputSupplier;
import shay.space.station.ui.infra.Output;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

@_Command
@Component
public class IssueCommandsToRobot implements RobotCommand {
    private static final String ISSUE_COMMANDS_TO_ROBOT = "Issue Commands To Robot";
    private final SpaceRobotsFleet m_fleet;
    private final InputSupplier m_input;
    private final Output m_output;
    private final RobotFactory robotFactory;
    private final MenuBuilder m_menuBuilder;
    private boolean goBack = false;

    private final LinkedHashMap<String, Consumer<Robot>> commands = new LinkedHashMap<>();

    public IssueCommandsToRobot(final Output output, final InputSupplier input,
                                final SpaceRobotsFleet fleet,
                                final RobotFactory robotFactory,
                                final MenuBuilder menuBuilder) {
        m_fleet = fleet;
        m_input = input;
        m_output = output;
        m_menuBuilder = menuBuilder;
        this.robotFactory = robotFactory;

        commands.put("Dispatch", Robot::dispatch);
        commands.put("Reboot", Robot::reboot);
        commands.put("Self-Diagnostics", Robot::selfDiagnosis);
        commands.put("Delete", (robot -> {
            m_fleet.deleteRobot(robot.getData().callSign());
            goBack = true;
        }));
        commands.put("Back to main menu", robot -> goBack = true);
    }

    @Override
    public void execute() {
        var robots = m_fleet.getAllRobots();
        if (robots.size() == 0) {
            m_output.print("Fleet is empty!");
            return;
        }
        final Robot robotSelected;
        try {
            robotSelected = getRobot(robots);
            m_output.print(robotSelected.getData().toString());
            do {
                final int indexCommand = getIndexCommand(robotSelected);
                // final var consumerList = commands.values().stream().toList();
                final List<Consumer<Robot>> consumerList = new ArrayList<>(commands.values());

                consumerList.get(indexCommand).accept(robotSelected);

            } while (!goBack);
        }
        catch (InvalidRobotSelectionException ex) {
            m_output.print(ex.getMessage());
        }

    }

    private int getIndexCommand(final Robot robotSelected) {
        m_output.print("Please select an action for robot " + robotSelected + ":");
        final var menuOfCommands = m_menuBuilder.Build(commands.keySet());
        m_output.print(menuOfCommands.toString());
        final var indexCommand = m_input.getNumber() - 1;
        return indexCommand;
    }

    private Robot getRobot(final List<Robot> robots) throws InvalidRobotSelectionException {
        displayRobotList(robots);

        final var indexRobot = m_input.getNumber() - 1;
        if (indexRobot >= 0 && indexRobot < robots.size()) {
            final var robotSelected = robots.get(indexRobot);
            return robotSelected;
        }
        else {
            throw new InvalidRobotSelectionException("Invalid robot selection");
        }

    }

    private void displayRobotList(final List<Robot> robots) {
        m_output.print("Please select a robot:");

        final List<String> robotInfoList = robotCallSignAndStatus(robots);
        final var menuOfRobots = m_menuBuilder.Build(robotInfoList);
        m_output.print(menuOfRobots.toString());
    }

    private List<String> robotCallSignAndStatus(final List<Robot> robots) {
        List<String> robotInfoList = new ArrayList<>();
        robots.forEach(robot -> {
            String robotInfo = "CallSign: " + robot.getData().callSign() + " | Status: " + robot.getStates().toString();
            robotInfoList.add(robotInfo);
        });
        return robotInfoList;
    }

    @Override
    public String toString() {
        return ISSUE_COMMANDS_TO_ROBOT;
    }
}
