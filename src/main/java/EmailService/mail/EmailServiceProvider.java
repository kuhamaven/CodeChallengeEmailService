package EmailService.mail;

import EmailService.models.Email;

public interface EmailServiceProvider {
    boolean sendEmail(Email email);
}