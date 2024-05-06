package EmailService.services;

import EmailService.mail.EmailModel;
import EmailService.mail.EmailSender;
import EmailService.models.User;
import EmailService.models.VerificationModel;
import EmailService.repositories.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.InternetAddress;

@Service
@Transactional
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final String BASE_URL = "Replace me eventually";

    @Autowired
    public VerificationService(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    public VerificationModel saveVerification(VerificationModel verificationModel){
        VerificationModel verification = this.verificationRepository.save(verificationModel);
        EmailSender sender = new EmailSender();
        String body = "Dear " + verification.getUser().getUsername() + ",<br>";
        body +=  "<br>Welcome to our <strong>Mail Service</strong>. <br><br>" +
                    "Click here: "+BASE_URL+"/verify/"+verification.getToken() +" to verify your account and get started." +
                    "<br><br>Thanks for choosing us.";
        body += "<br><br><i>~Mail Service.</i>";
        try {
            EmailModel email = new EmailModel(new InternetAddress(verification.getUser().getEmail()),"Welcome to Mail Service",body);
            //sender.notifyWithGmail(email);
        } catch (Exception e) {
        }
        return verification;
    }

    public VerificationModel findVerificationByToken(String token){
        return verificationRepository.findByToken(token).orElseThrow(() -> new RuntimeException("¡El token solicitado no es valido!"));
    }

    public VerificationModel findVerificationByUserModel(User user){
        return verificationRepository.findByUser(user).orElseThrow(() -> new RuntimeException("¡El usuario solicitado no necesita ser verificado!"));
    }
}
