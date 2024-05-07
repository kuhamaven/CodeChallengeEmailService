package EmailService.controllers;

import EmailService.dtos.MetricsDto;
import EmailService.services.MetricsService;
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

    @GetMapping("/daily")
    public List<MetricsDto> getGlobalMetrics(){
        return metricsService.getDailyMetrics();
    }

    @GetMapping("")
    public List<MetricsDto> getUserMetrics(){
        return metricsService.getUserMetrics();
    }

}
