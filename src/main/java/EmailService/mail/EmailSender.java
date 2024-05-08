package EmailService.mail;

import EmailService.exceptions.BadRequestException;
import java.util.Arrays;
import java.util.List;

public class EmailSender {

    private static final List<EmailServiceProvider> serviceList = Arrays.asList(
            new MailgunService(),
            new SendGridService()
    );

    public static void sendEmail(EmailService.models.Email email){
        // Iterates through all services. If one can't send the mail it keeps trying with the next one.
        for (EmailServiceProvider emailService : serviceList) {
            if (emailService.sendEmail(email)) return;
        }

        // In case no service could send it, throws the required error.
        throw new BadRequestException("None of our services are available, please try again later.");
    }

}