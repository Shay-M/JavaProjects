package shay.space.station.robots.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shay.space.station.infra.base.Robot;
import shay.space.station.robots.models.Hal9000;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RobotModelConfig {

    @Bean
    public Map<String, Class<? extends Robot>> robotTypes() {
        Map<String, Class<? extends Robot>> robotTypes = new HashMap<>();
        robotTypes.put("HAL9000", Hal9000.class);
        // robotTypes.put("TACHIKOMAS", Tachikomas.class);
        // robotTypes.put("JOHNNY5", Johnny5.class);
        // robotTypes.put("MASCHINENMENSCH", Maschinenmensch.class);
        // Add more robot models here
        return robotTypes;
    }


}

