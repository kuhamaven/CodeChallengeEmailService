package EmailService.repositories;

import EmailService.models.DailyMetrics;
import EmailService.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyMetricsRepository extends PagingAndSortingRepository<DailyMetrics, String> {


     List<DailyMetrics> findAllByDate(LocalDate date);

     List<DailyMetrics> findAllByUser(User user);

     Optional<DailyMetrics> findByDateAndUser(LocalDate date, User user);
}
