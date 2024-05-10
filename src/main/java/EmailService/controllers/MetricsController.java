package EmailService.controllers;

import EmailService.dtos.MetricsDto;
import EmailService.security.PermissionsValidator;
import EmailService.services.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
    private final MetricsService metricsService;
    private final PermissionsValidator permissionsValidator;

    @Autowired
    public MetricsController(MetricsService metricsService, PermissionsValidator permissionsValidator) {
        this.metricsService = metricsService;
        this.permissionsValidator = permissionsValidator;
    }

    @GetMapping("/daily")
    public List<MetricsDto> getGlobalMetrics(){
        permissionsValidator.isAdmin();
        return metricsService.getDailyMetrics();
    }

    @GetMapping("")
    public List<MetricsDto> getUserMetrics(){
        permissionsValidator.isAdmin();
        return metricsService.getUserMetrics();
    }

}
