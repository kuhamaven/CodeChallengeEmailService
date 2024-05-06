package EmailService.services;

import EmailService.dtos.EmailDto;
import EmailService.exceptions.BadRequestException;
import EmailService.mail.EmailSender;
import EmailService.models.Email;
import EmailService.models.User;
import EmailService.repositories.EmailRepository;
import EmailService.repositories.UserRepository;
import EmailService.security.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
@Transactional
public class
EmailService {

    private final SessionUtils sessionUtils;
    private final EmailRepository emailRepository;
    private final MetricsService metricsService;

    @Autowired
    public EmailService(UserRepository userRepository, EmailRepository emailRepository, MetricsService metricsService) {
        this.emailRepository = emailRepository;
        this.sessionUtils = new SessionUtils(userRepository);
        this.metricsService = metricsService;
    }

    public void sendEmail(EmailDto dto) {
        try{
            //TODO when using lists of recipients replace 1 by recipients.size()
            if(metricsService.getEmails()+1>=findLoggedUser().getDailyQuota()) throw new BadRequestException("User exceeded quota!");
            Email email = new Email(dto.getSubject(),dto.getBody(),dto.getRecipient(),findLoggedUser());
            EmailSender.sendGrid(email);
            metricsService.sendEmail(email.getRecipients().size());
        } catch (IOException e) {
            //TODO fallback to another service
            e.printStackTrace();
        }
    }

    public User findLoggedUser() {
        User user = sessionUtils.getCurrentUser();
        return user;
    }
}
