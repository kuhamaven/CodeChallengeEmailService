package EmailService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    @Autowired
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/Global")
    public MetricsDto getGlobalMetrics(){
        return metricsService.getMetrics();
    }

    @GetMapping("/Balance")
    public BalanceMetricsDto getBalanceMetrics(){
        return metricsService.getBalanceMetrics();
    }

    @GetMapping("/Popularity")
    public List<DigimonUseAndWins> getChildsPopularity(){
        return metricsService.getPopularityMetrics();
    }
}
