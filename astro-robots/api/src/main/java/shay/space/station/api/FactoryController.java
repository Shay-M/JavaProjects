package shay.space.station.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shay.space.station.infra.base.Robot;
import shay.space.station.robots.factory.RobotFactory;

import java.util.List;

@RestController
@RequestMapping("/api/factory/")
@CrossOrigin(origins = "http://localhost:5173")
public class FactoryController {

    private final RobotFactory m_robotFactory;

    public FactoryController(final RobotFactory robotFactory) {
        m_robotFactory = robotFactory;
    }

    @GetMapping("/specific-models")
    public ResponseEntity<List<String>> getSpecificModels() {
        var models = m_robotFactory.getAllModels();
        return ResponseEntity.ok(models);
    }
}
