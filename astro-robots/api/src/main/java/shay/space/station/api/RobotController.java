package shay.space.station.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shay.space.station.command.command.ProvisionNewRobot;
import shay.space.station.fleet.SpaceRobotsFleet;
import shay.space.station.fleet.exception.DuplicateRobotException;
import shay.space.station.infra.base.Robot;
import shay.space.station.infra.base.RobotData;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class RobotController {

    private final SpaceRobotsFleet m_fleet;
    private final ProvisionNewRobot m_provisionNewRobot;

    public RobotController(final SpaceRobotsFleet fleet, final ProvisionNewRobot provisionNewRobot) {
        m_fleet = fleet;
        m_provisionNewRobot = provisionNewRobot;
    }

    @GetMapping("/robots")
    public ResponseEntity<List<Robot>> getAllRobots() {
        List<Robot> robots = m_fleet.getAllRobots();
        return ResponseEntity.ok(robots);
    }

    // @GetMapping("/robots-details")
    // public ResponseEntity<List<Robot>> getAllRobots() {
    //     List<Robot> robots = m_fleet.getAllRobots();
    //     return ResponseEntity.ok(robots);
    // }

    @PostMapping("/robots")
    public ResponseEntity<String> addRobot(@RequestBody RobotData robot) {
        System.out.println(robot);
        try {
            m_provisionNewRobot.createAndAddRobot(robot);
            return ResponseEntity.ok("Robot added successfully");
        }
        catch (DuplicateRobotException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/robots/{callSign}")
    public ResponseEntity<Robot> selectRobot(@PathVariable String callSign) {
        Optional<Robot> selectedRobot = m_fleet.selectRobot(callSign);
        return selectedRobot
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/robots/{callSign}/commands")
    public ResponseEntity<String> issueCommandToRobot(@PathVariable String callSign, @RequestBody String command) {
        Optional<Robot> selectedRobot = m_fleet.selectRobot(callSign);
        if (selectedRobot.isPresent()) {
            // Implement the logic to issue the specified command to the selected robot
            // For example: selectedRobot.get().dispatch();
            return ResponseEntity.ok("Command issued successfully");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/robots/{callSign}/reboot")
    public ResponseEntity<String> rebootRobot(@PathVariable String callSign) {
        Optional<Robot> selectedRobot = m_fleet.selectRobot(callSign);
        if (selectedRobot.isPresent()) {
            selectedRobot.get().reboot(); // Assuming you have a reboot method in Robot class
            return ResponseEntity.ok("Robot reboot initiated");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/robots/{callSign}/self-diagnostics")
    public ResponseEntity<String> runSelfDiagnostics(@PathVariable String callSign) {
        Optional<Robot> selectedRobot = m_fleet.selectRobot(callSign);
        if (selectedRobot.isPresent()) {
            // Implement the logic to initiate self-diagnostics on the selected robot
            // For example: selectedRobot.get().runSelfDiagnostics();
            return ResponseEntity.ok("Self-diagnostics initiated");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/robots/{callSign}")
    public ResponseEntity<String> updateRobotInfo(@PathVariable String callSign, @RequestBody Robot updatedRobot) {
        Optional<Robot> selectedRobot = m_fleet.selectRobot(callSign);
        if (selectedRobot.isPresent()) {
            // Update the robot's information with the provided updatedRobot data
            // For example: selectedRobot.get().updateInfo(updatedRobot);
            return ResponseEntity.ok("Robot information updated");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
