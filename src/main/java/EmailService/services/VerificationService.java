package EmailService.services;

import EmailService.mail.EmailSender;
import EmailService.models.Email;
import EmailService.models.User;
import EmailService.models.VerificationModel;
import EmailService.repositories.UserRepository;
import EmailService.repositories.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public VerificationService(VerificationRepository verificationRepository, UserRepository userRepository) {
        this.verificationRepository = verificationRepository;
        this.userRepository = userRepository;
    }

    public VerificationModel saveVerification(VerificationModel verificationModel){
        VerificationModel verification = this.verificationRepository.save(verificationModel);
        String body = "Dear " + verification.getUser().getUsername() + ",<br>";
        body +=  "<br>Welcome to our <strong>Mail Service</strong>. <br><br>" +
                    "Click here: "+System.getenv("BASE_URL")+"/verify/"+verification.getToken() +" to verify your account and get started." +
                    "<br><br>Thanks for choosing us.";
        body += "<br><br><i>~Mail Service.</i>";
        try {
            Email email = new Email("Welcome to Mail Service",body,verification.getUser().getEmail(),userRepository.findByEmail("admin@mailservice.com").get());
            EmailSender.sendGrid(email);
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

    public void deleteVerification(VerificationModel verificationModel){
        verificationRepository.delete(verificationModel);
    }
}
