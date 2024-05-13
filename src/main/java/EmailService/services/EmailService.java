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
        User user = findLoggedUser();

        try{
            //TODO if using lists of recipients replace 1 by recipients.size()

            //Prevent user from exceeding quota.
            if(metricsService.getEmails()+1>findLoggedUser().getDailyQuota())
                throw new BadRequestException("User exceeded quota!");

            Email email = new Email(dto.getSubject(),dto.getBody(),dto.getRecipient(),user);
            EmailSender.sendEmail(email);

            //If exception gets thrown, this gets ignored keeping DB clean.
            email = emailRepository.save(email);
            metricsService.sendEmail(email.getRecipients().size());
            user.addEmail(email);

        } catch (BadRequestException e) {
            throw e;
        }
    }

    public User findLoggedUser() {
        User user = sessionUtils.getCurrentUser();
        return user;
    }
}
