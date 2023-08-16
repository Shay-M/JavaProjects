package shay.space.station.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shay.space.station.command.CommandsManager;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/commands")
@CrossOrigin(origins = "http://localhost:5173")
public class CommandController {

    private final CommandsManager commandsManager;

    public CommandController(CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
    }

    @GetMapping
    public List<String> getAllCommands() {
        return commandsManager.getAllCommands();
    }
}

