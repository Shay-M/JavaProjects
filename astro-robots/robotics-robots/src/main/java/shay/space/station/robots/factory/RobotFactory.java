package shay.space.station.robots.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import shay.space.station.infra.base.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Configuration
@ComponentScan
//@Component
public class RobotFactory {

    private final BeanFactory m_beanFactory;
    private final Map<String, Class<? extends Robot>> m_robotTypes;
    private final List<Robot> robots;

    @Autowired
    public RobotFactory(final BeanFactory beanFactory,
                        final Map<String, Class<? extends Robot>> robotTypes,
                        final List<Robot> robots) {
        m_beanFactory = beanFactory;
        m_robotTypes = robotTypes;

        this.robots = robots;
    }

    public Robot createRobot(final String model, final String name, final String callSign) {
        final Class<? extends Robot> robotType = m_robotTypes.get(model);
        if (robotType == null) {
            throw new IllegalArgumentException("Invalid robot model: " + model);
        }

        final var robot = m_beanFactory.getBean(robotType);
        robot.build(name, callSign);

        return robot;
    }

    public List<String> getAllModels() {
        // System.out.println(this.robots);
        return new ArrayList<>(m_robotTypes.keySet());
    }
}
