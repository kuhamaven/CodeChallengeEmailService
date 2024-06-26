package EmailService.models;

import EmailService.dtos.UserRegisterDto;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(nullable = false)
    private String id;

    private boolean active;

    private String email;

    private String username;

    private String password;

    private UserType type;

    private int dailyQuota;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Email> sentMails;

    public User() {
        active = true;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        this.type = UserType.USER;
        this.dailyQuota = 1000;
        this.active = false;
        sentMails = new HashSet<>();
    }

    public User(String email, String username, String password, int dailyQuota) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dailyQuota = dailyQuota;

        this.type = UserType.USER;
        this.active = false;
        sentMails = new HashSet<>();
    }

    public static User fromDto(UserRegisterDto userDto) {
        return new User(userDto.getEmail().toLowerCase(), userDto.getUsername().toLowerCase(), userDto.getPassword());
    }

    public void addEmail(Email email){
        if(sentMails==null) sentMails = new HashSet<>();
        sentMails.add(email);
    }

    public enum UserType{
        USER,
        ADMIN
    }

}
