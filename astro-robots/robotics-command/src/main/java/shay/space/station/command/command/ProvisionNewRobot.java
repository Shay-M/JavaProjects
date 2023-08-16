package shay.space.station.command.command;

import org.springframework.stereotype.Component;
import shay.space.station.command.command.exception.InvalidModelIndexException;
import shay.space.station.command.infra.RobotCommand;
import shay.space.station.command.utils.MenuBuilder;
import shay.space.station.core.annotations._Command;
import shay.space.station.fleet.exception.DuplicateRobotException;
import shay.space.station.fleet.SpaceRobotsFleet;
import shay.space.station.infra.base.RobotData;
import shay.space.station.robots.factory.RobotFactory;
import shay.space.station.ui.infra.InputSupplier;
import shay.space.station.ui.infra.Output;

@_Command
@Component
public class ProvisionNewRobot implements RobotCommand {
    private static final String CREATING_A_NEW_ROBOT = "Creating a new robot";
    private static final String INVALID_MODEL_INDEX = "Invalid model index.";
    private static final String ENTER_THE_ROBOT_NAME_2_32_CHARACTERS = "Enter the robot name: (2-32 characters)";
    private static final String ENTER_THE_ROBOT_CALL_SIGN_MUST_BE_UNIQUE = "Enter the robot call sign: (must be unique)";
    private static final String CHOOSE_A_ROBOT_MODEL_FROM_THIS_LIST = "Choose a robot model from this list: ";
    private static final String PROVISION_NEW_ROBOT = "Provision new robot";
    private static final int MIN_LENGTH_NAME = 2;
    private static final int MAX_LENGTH_NAME = 32;

    private final InputSupplier m_input;
    private final Output m_output;
    private final SpaceRobotsFleet m_fleet;
    private final RobotFactory m_robotFactory;
    private final MenuBuilder m_menuBuilder;

    public ProvisionNewRobot(final Output output, final InputSupplier input,
                             final SpaceRobotsFleet fleet,
                             final RobotFactory robotFactory,
                             final MenuBuilder menuBuilder) {
        m_fleet = fleet;
        m_input = input;
        m_output = output;
        m_robotFactory = robotFactory;
        m_menuBuilder = menuBuilder;
    }

    @Override
    public void execute() {
        m_output.print(CREATING_A_NEW_ROBOT);
        final String name = getRobotName();
        final String callSign = getCallSign();
        try {
            final int modelIndex = getModelIndex();
            final var model = m_robotFactory.getAllModels().get(modelIndex);

            createAndAddRobot(model, name, callSign);

            final var robot = m_robotFactory.createRobot(model, name, callSign);
            m_fleet.addRobot(robot);
        }
        catch (InvalidModelIndexException ex) {
            m_output.print(INVALID_MODEL_INDEX);
        }
        catch (DuplicateRobotException ex) {
            m_output.print(ex.getMessage());
        }

    }

    public void createAndAddRobot(final RobotData robotData) {
        createAndAddRobot(robotData.model(), robotData.name(), robotData.callSign());
    }

    private void createAndAddRobot(final String model, final String name, final String callSign) {
        final var robot = m_robotFactory.createRobot(model, name, callSign);
        m_fleet.addRobot(robot);


    }

    private String getRobotName() {
        String name;
        do {
            m_output.print(ENTER_THE_ROBOT_NAME_2_32_CHARACTERS);
            name = m_input.getString();
        } while (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME);
        return name;
    }

    private String getCallSign() {
        String callSign;
        do {
            m_output.print(ENTER_THE_ROBOT_CALL_SIGN_MUST_BE_UNIQUE);
            callSign = m_input.getString();
        } while (callSign.isBlank());
        return callSign;
    }

    private int getModelIndex() throws InvalidModelIndexException {
        m_output.print(CHOOSE_A_ROBOT_MODEL_FROM_THIS_LIST);

        var modelsMenuBuilder = m_menuBuilder.Build(m_robotFactory.getAllModels());
        m_output.print(modelsMenuBuilder.toString());

        final var modelIndex = m_input.getNumber() - 1;

        if (modelIndex < 0 || modelIndex > m_robotFactory.getAllModels().size()) {
            throw new InvalidModelIndexException();
        }

        return modelIndex;
    }


    @Override
    public String toString() {
        return PROVISION_NEW_ROBOT;
    }
}
