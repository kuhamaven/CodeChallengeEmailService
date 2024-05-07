package EmailService.controllers;

import EmailService.exceptions.BadRequestException;
import EmailService.models.User;
import EmailService.models.VerificationModel;
import EmailService.services.UserService;
import EmailService.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VerificationController {

    private final UserService userService;
    private final VerificationService verificationService;

    @Autowired
    public VerificationController(UserService userService, VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity verifyAccount(@PathVariable String token){
        try{
            VerificationModel verificationModel = verificationService.findVerificationByToken(token);
            User user = verificationModel.getUser();
            user.setActive(true);
            userService.saveWithoutEncryption(user);
            verificationService.deleteVerification(verificationModel);
            return ResponseEntity.ok("Tu email fue validado exitosamente, ya puedes iniciar sesión.");
        }catch (RuntimeException e){
            return new ResponseEntity(new BadRequestException("El token solicitado no es válido."),HttpStatus.BAD_REQUEST);
        }
    }

}
