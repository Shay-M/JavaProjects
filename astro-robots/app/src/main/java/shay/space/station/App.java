package shay.space.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import shay.space.station.robots.factory.RobotsMaker;
import shay.space.station.ui.console.Output;

@Component
public class App implements CommandLineRunner {
    private final RobotsMaker robotsMaker;

    public App(final RobotsMaker robotsMaker) {
        this.robotsMaker = robotsMaker;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("start!");

        // final var robot = robotsMaker.hal9k("bot1", "101Am");
        final var robot = robotsMaker.createRobot("HAL9000", "bot1", "101Am");

        System.out.println(robot);
        robot.dispatch();

        // final var robot2 = robotsMaker.createRobot("HAL9000", "bot2", "101Am");
        //
        // System.out.println(robot2);
        // robot2.dispatch();


    }
}
