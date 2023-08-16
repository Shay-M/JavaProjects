package shay.space.station.robots.factory;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shay.space.station.infra.base.Robot;
import shay.space.station.robots.models.Hal9000;
import shay.space.station.robots.models.Johnny5;
import shay.space.station.robots.models.Maschinenmensch;
import shay.space.station.robots.models.Tachikomas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class RobotModelConfig {
        // @Bean
    // public Map<String, Class<? extends Robot>> robotTypes() {
    //     Map<String, Class<? extends Robot>> robotTypes = new HashMap<>();
    //     robotTypes.put("HAL9000", Hal9000.class);
    //     robotTypes.put("TACHIKOMAS", Tachikomas.class);
    //     robotTypes.put("JOHNNY5", Johnny5.class);
    //     robotTypes.put("MASCHINENMENSCH", Maschinenmensch.class);
    //     // Add more robot models here
    //     return robotTypes;
    // }

    @Bean
    public Map<String, Class<? extends Robot>> robotTypes(ListableBeanFactory beanFactory) {
        Map<String, Class<? extends Robot>> robotTypes = new HashMap<>();

        Map<String, Robot> robotBeans = beanFactory.getBeansOfType(Robot.class);
        for (Map.Entry<String, Robot> entry : robotBeans.entrySet()) {
            String modelName = entry.getKey().toUpperCase();
            Class<? extends Robot> modelClass = entry.getValue().getClass();
            robotTypes.put(modelName, modelClass);
        }

        return robotTypes;
    }


}

