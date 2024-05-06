package EmailService.mail;

import javax.mail.internet.InternetAddress;

public class EmailModel {
    InternetAddress recipient;
    String subject;
    String body;

    public EmailModel() {
    }

    public EmailModel(InternetAddress recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public InternetAddress getRecipient() {
        return recipient;
    }

    public void setRecipient(InternetAddress recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}