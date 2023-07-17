package shay.finalproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shay.finalproject.controller.infrastructure.Statistics;
import shay.finalproject.controller.infrastructure.StatisticsService;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }
}

