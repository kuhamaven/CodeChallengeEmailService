package EmailService.mail;


import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;

public class MailerSendService implements EmailServiceProvider {

    @Override
    public boolean sendEmail(EmailService.models.Email email) {
        for (String recipient : email.getRecipients()) {
            Email mailerSend = new Email();

            mailerSend.setFrom(email.getUser().getUsername(), System.getenv("ADMIN_EMAIL"));
            mailerSend.addRecipient(recipient, recipient);

            mailerSend.setSubject(email.getSubject());

            mailerSend.setHtml(email.getBody());

            MailerSend ms = new MailerSend();

            ms.setToken(System.getenv("MAILER_SEND_API_KEY"));

            try {
                MailerSendResponse response = ms.emails().send(mailerSend);
                System.out.println(response.messageId);
            } catch (MailerSendException e) {
                e.printStackTrace();
                return false;
            }

        }

        return true;
    }

}
