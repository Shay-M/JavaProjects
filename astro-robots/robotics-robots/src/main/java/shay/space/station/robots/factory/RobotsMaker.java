package shay.space.station.robots.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import shay.space.station.infra.base.Robot;
import shay.space.station.robots.models.Hal9000;

import java.util.Map;


@Configuration
@ComponentScan
//@Component
public class RobotsMaker {

    private final BeanFactory beanFactory;
    private final Map<String, Class<? extends Robot>> robotTypes;


    public RobotsMaker(final BeanFactory beanFactory, final Map<String, Class<? extends Robot>> robotTypes) {
        this.beanFactory = beanFactory;
        this.robotTypes = robotTypes;
    }

    // public Robot hal9k(final String name, final String callSign) {
    //     final var robot = beanFactory.getBean(Hal9000.class);
    //     robot.build(name, callSign);
    //
    //     return robot;
    //
    // }

    public Robot createRobot(final String model, final String name, final String callSign) {
        final Class<? extends Robot> robotType = robotTypes.get(model);
        if (robotType == null) {
            throw new IllegalArgumentException("Invalid robot model: " + model);
        }

        final var robot = beanFactory.getBean(robotType);
        robot.build(name, callSign);

        return robot;
    }
}
