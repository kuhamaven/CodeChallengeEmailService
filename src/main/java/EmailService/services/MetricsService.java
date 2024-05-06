package EmailService.services;

import EmailService.models.DailyMetrics;
import EmailService.models.User;
import EmailService.repositories.DailyMetricsRepository;
import EmailService.security.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class
MetricsService {

    private final DailyMetricsRepository dailyMetricsRepository;
    private final SessionUtils sessionUtils;

    @Autowired
    public MetricsService(DailyMetricsRepository balanceMetricsRepository, SessionUtils sessionUtils) {
        this.dailyMetricsRepository = balanceMetricsRepository;
        this.sessionUtils = sessionUtils;
    }

    public void sendEmail(int amount) {
        if (amount <= 0) return;
        DailyMetrics metrics = getOrCreateGlobalMetrics();
        metrics.addEmailsSent(amount);
        dailyMetricsRepository.save(metrics);
    }

    private DailyMetrics getOrCreateGlobalMetrics() {
        User user = sessionUtils.getCurrentUser();
        Optional<DailyMetrics> metrics = dailyMetricsRepository.findAllByDateAndUser(LocalDate.now(),user);
        if(metrics.isEmpty()) return dailyMetricsRepository.save(new DailyMetrics(user));
        else return metrics.get();
    }

}
