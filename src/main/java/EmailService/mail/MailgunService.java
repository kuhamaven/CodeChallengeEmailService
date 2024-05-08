package EmailService.mail;

import EmailService.models.Email;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;

public class MailgunService implements EmailService {

    @Override
    public boolean sendEmail(Email email) {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(System.getenv("MAILGUN_API_KEY"))
                .createApi(MailgunMessagesApi.class);

        for (String recipient : email.getRecipients()) {
            Message message = Message.builder()
                    .from(System.getenv("ADMIN_EMAIL"))
                    .to(recipient)
                    .subject(email.getSubject())
                    .text(email.getBody())
                    .build();

            MessageResponse messageResponse = mailgunMessagesApi.sendMessage(System.getenv("MAILGUN_DOMAIN"), message);
            return true;
        }

        return false;
    }
}
