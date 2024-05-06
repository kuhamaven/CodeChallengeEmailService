package EmailService.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class EmailSender {


    public static void sendGrid(EmailService.models.Email email) throws IOException {
        Email from = new Email(email.getUser().getEmail());

        email.getRecipients().forEach(recipient -> {
            Email to = new Email(recipient);
            Content content = new Content("text/plain", email.getBody());
            Mail mail = new Mail(from, email.getSubject(), to, content);

            SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}