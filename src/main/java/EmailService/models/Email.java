package EmailService.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Email {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(nullable = false)
    private String id;

    private String subject;

    private String body;

    @ElementCollection
    @CollectionTable(name = "recipients", joinColumns = @JoinColumn(name = "email_id"))
    private Set<String> recipients;

    @ManyToOne
    private User user;

    public Email(){

    }

    public Email(String subject, String body, String recipient, User user){
        this.subject = subject;
        this.body = body;
        this.recipients = new HashSet<>();
        recipients.add(recipient);
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return getId() != null ? getId().equals(email.getId()) : email.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
