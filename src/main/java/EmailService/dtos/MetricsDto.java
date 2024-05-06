package alicestudios.EmailService.dtos;

import alicestudios.EmailService.models.GlobalMetrics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricsDto {
    private int
            brokenDrivers,
            totalEarnedBits,
            totalBattles,
            harvestedCrops,
            unlockedAchievements,
            cleanedPoops,
            totalEvolutions,
            totalDeaths,
            uniqueUsers,
            completedCycles,
            totalDestroyedBit,
            numemons;

    private LocalDate date;

    public static MetricsDto toDto(GlobalMetrics metrics) {
        return new MetricsDto(
                metrics.getBrokenDrivers(),
                metrics.getTotalEarnedBits(),
                metrics.getTotalBattles(),
                metrics.getHarvestedCrops(),
                metrics.getUnlockedAchievements(),
                metrics.getCleanedPoops(),
                metrics.getTotalEvolutions(),
                metrics.getTotalDeaths(),
                metrics.getUniqueUsers(),
                metrics.getCompletedCycles(),
                metrics.getTotalDestroyedBits(),
                metrics.getNumemons(),
                metrics.getDate()
        );
    }
}