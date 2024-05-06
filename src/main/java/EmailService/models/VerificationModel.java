package EmailService.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
public class VerificationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @Column
    private String token;

    @OneToOne
    private User user;

    public VerificationModel() {
    }

    public VerificationModel(User user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) { this.token = token; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
