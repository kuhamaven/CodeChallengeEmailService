package EmailService.security;

import EmailService.exceptions.BadRequestException;
import EmailService.models.User;
import EmailService.repositories.UserRepository;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionUtils {

    private final UserRepository userRepository;

    @Autowired
    public SessionUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((DefaultClaims)authentication.getPrincipal()).get("sub").toString();
        return userRepository.findByUsername(username).orElseThrow(()-> new BadRequestException("Invalid Token"));
    }
}
