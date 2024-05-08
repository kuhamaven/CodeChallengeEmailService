package EmailService.mail;

import EmailService.models.Email;

public interface EmailService {
    boolean sendEmail(Email email);
}