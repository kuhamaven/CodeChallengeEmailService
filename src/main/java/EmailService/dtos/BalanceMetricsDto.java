package alicestudios.EmailService.dtos;

import alicestudios.EmailService.models.BalanceMetrics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceMetricsDto {

    private LocalDate date;

    private Set<DriverMetricsDto> driverMetrics;

    private Set<DigimonMetricsDto> digimonMetrics;

    private int childs, adults, perfects;

    public static BalanceMetricsDto toDto(BalanceMetrics metrics) {
        return new BalanceMetricsDto(
                metrics.getDate(),
                metrics.getDriverMetrics().stream().map(DriverMetricsDto::toDto).collect(Collectors.toSet()),
                metrics.getDigimonMetrics().stream().map(DigimonMetricsDto::toDto).collect(Collectors.toSet()),
                metrics.getChilds(),
                metrics.getAdults(),
                metrics.getPerfects()
        );
    }
}