package EmailService.dtos;

import EmailService.models.DailyMetrics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricsDto {
    private LocalDate date;
    private int emailsSent;
    private String username;


    public static MetricsDto toDto(DailyMetrics metrics) {
        return new MetricsDto(
                metrics.getDate(),
                metrics.getEmailsSent(),
                metrics.getUser().getUsername()
        );
    }
}