package shay.space.station.command.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shay.space.station.command.infra.RobotCommand;
import shay.space.station.core.annotations._Command;
import shay.space.station.ui.infra.Output;

@_Command
@Component
public class Quit implements RobotCommand {

    private static final String QUIT = "Quit";
    private static final String THANK_YOU = "Thank you for using the Robot Fleet Management System!";
    @Autowired
    private Output m_output;


    @Override
    public void execute() {
        m_output.print(THANK_YOU);
        System.exit(0);

    }

    @Override
    public String toString() {
        return QUIT;
    }
}
