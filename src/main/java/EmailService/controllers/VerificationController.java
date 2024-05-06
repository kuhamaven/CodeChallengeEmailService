package alicestudios.EmailService.controllers;

import alicestudios.EmailService.models.PasswordContainer;
import alicestudios.EmailService.models.VerificationModel;
import alicestudios.EmailService.services.VerificationService;
import alicestudios.EmailService.exceptions.BadRequestException;
import alicestudios.EmailService.models.User;
import alicestudios.EmailService.services.UserService;
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

    @PutMapping("/reset/{token}")
    public ResponseEntity resetPassword(@PathVariable String token, @RequestBody PasswordContainer passwordContainer){
        try{
            VerificationModel verificationModel = verificationService.findVerificationByToken(token);
            User user = verificationModel.getUser();
            if (passwordContainer.getPassword().length()<6) return new ResponseEntity(new BadRequestException("¡La contraseña ingresada no es valida, por favor intente con otra!"), HttpStatus.BAD_REQUEST);
            user.setPassword(passwordContainer.getPassword());
            userService.updatePassword(user);
            verificationService.deleteVerification(verificationModel);
            return ResponseEntity.ok(user);
        }catch (RuntimeException e){
            return new ResponseEntity(new BadRequestException("¡El token solicitado no es valido!"), HttpStatus.BAD_REQUEST);
        }
    }

}
