package EmailService.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
public class DailyMetrics {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(nullable = false)
    private String id;

    @CreationTimestamp
    private LocalDate date;

    @ManyToOne
    private User user;

    private int emailsSent;

    public DailyMetrics(User user) {
        this.user = user;
    }

    public DailyMetrics() {

    }

    public void addEmailsSent(int amount) {
        emailsSent += amount;
    }
}
