package shay.space.station;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import shay.space.station.command.CommandsManager;
import shay.space.station.fleet.SpaceRobotsFleet;
import shay.space.station.fleet.exception.DuplicateRobotException;
import shay.space.station.robots.factory.RobotFactory;

@Component
public class App implements CommandLineRunner {
    private final RobotFactory m_robotFactory;
    private final SpaceRobotsFleet m_fleet;
    private final CommandsManager m_commandsManager;

    public App(final RobotFactory robotFactory, final SpaceRobotsFleet fleet, final CommandsManager commandsManager) {
        m_robotFactory = robotFactory;
        m_fleet = fleet;
        m_commandsManager = commandsManager;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("start!");

        // System.out.println(m_robotFactory.getAllModels());

        try {
            final var robot = m_robotFactory.createRobot("HAL9000", "bot1", "101Am");
            m_fleet.addRobot(robot);

            final var robot2 = m_robotFactory.createRobot("HAL9000", "bot2", "102Am");
            m_fleet.addRobot(robot2);


        }
        catch (DuplicateRobotException ex) {
            System.out.println(ex.getMessage());
        }

        m_commandsManager.start();
    }
}
