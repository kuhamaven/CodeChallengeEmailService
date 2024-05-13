package EmailService.controllers;

import EmailService.dtos.MetricsDto;
import EmailService.security.PermissionsValidator;
import EmailService.services.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class MetricsController {
    private final MetricsService metricsService;
    private final PermissionsValidator permissionsValidator;

    @Autowired
    public MetricsController(MetricsService metricsService, PermissionsValidator permissionsValidator) {
        this.metricsService = metricsService;
        this.permissionsValidator = permissionsValidator;
    }

    @GetMapping("")
    public List<MetricsDto> getGlobalMetrics(){
        permissionsValidator.isAdmin();
        return metricsService.getDailyMetrics();
    }
}
