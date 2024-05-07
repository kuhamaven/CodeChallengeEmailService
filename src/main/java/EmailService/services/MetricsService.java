package EmailService.services;

import EmailService.dtos.MetricsDto;
import EmailService.models.DailyMetrics;
import EmailService.models.User;
import EmailService.repositories.DailyMetricsRepository;
import EmailService.repositories.UserRepository;
import EmailService.security.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class
MetricsService {

    private final DailyMetricsRepository dailyMetricsRepository;
    private final SessionUtils sessionUtils;

    @Autowired
    public MetricsService(DailyMetricsRepository balanceMetricsRepository, UserRepository userRepository) {
        this.dailyMetricsRepository = balanceMetricsRepository;
        this.sessionUtils = new SessionUtils(userRepository);
    }

    public void sendEmail(int amount) {
        if (amount <= 0) return;
        DailyMetrics metrics = getOrCreateGlobalMetrics();
        metrics.addEmailsSent(amount);
        dailyMetricsRepository.save(metrics);
    }

    private DailyMetrics getOrCreateGlobalMetrics() {
        User user = sessionUtils.getCurrentUser();
        Optional<DailyMetrics> metrics = dailyMetricsRepository.findByDateAndUser(LocalDate.now(),user);
        if(metrics.isEmpty()) return dailyMetricsRepository.save(new DailyMetrics(user));
        else return metrics.get();
    }

    public int getEmails() {
        return getOrCreateGlobalMetrics().getEmailsSent();
    }

    public List<MetricsDto> getDailyMetrics() {
        return dailyMetricsRepository.findAllByDate(LocalDate.now()).stream().map(MetricsDto::toDto).collect(Collectors.toList());
    }

    public List<MetricsDto> getUserMetrics() {
        return dailyMetricsRepository.findAllByUser(sessionUtils.getCurrentUser()).stream().map(MetricsDto::toDto).collect(Collectors.toList());
    }
}
