package shay.space.station.command;


import org.springframework.stereotype.Service;
import shay.space.station.command.infra.RobotCommand;
import shay.space.station.command.utils.MenuBuilder;
import shay.space.station.core.annotations._Command;
import shay.space.station.ui.infra.InputSupplier;
import shay.space.station.ui.infra.Output;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommandsManager {
    private final Output m_output;
    private final InputSupplier m_input;
    private final List<RobotCommand> m_commands;
    private final MenuBuilder m_menuBuilder;

    public CommandsManager(final Output output,
                           final InputSupplier input,
                           @_Command final List<RobotCommand> commands,
                           final MenuBuilder menuBuilder
    ) {
        m_output = output;
        m_input = input;
        m_commands = commands;
        m_menuBuilder = menuBuilder;
    }

    public void commandNumb(final int index) {
        if (index >= 0 && index < m_commands.size()) {
            m_commands.get(index).execute();
        }
        else {
            m_output.print("Invalid command index.");
        }
    }

    public void start() {
        while (true) {
            m_output.print("\n");
            m_output.print("===============================================");
            m_output.print(" Welcome to the Robot Fleet Management System!");
            m_output.print("===============================================");
            m_output.print("Please select an option:\n");

            var commandsBuilder = m_menuBuilder.Build(m_commands);
            m_output.print(commandsBuilder.toString());
            var manuIndex = m_input.getNumber() - 1;
            m_input.getString();
            commandNumb(manuIndex);
        }
    }

    public List<String> getAllCommands() {
        List<String> commandNames = new ArrayList<>();
        for (RobotCommand command : m_commands) {
            commandNames.add(command.toString());
        }
        return commandNames;
    }
}
