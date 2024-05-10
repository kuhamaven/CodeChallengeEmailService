package EmailService.security;

import EmailService.exceptions.BadRequestException;
import EmailService.exceptions.UnauthorizedException;
import EmailService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionsValidator {

    private final SessionUtils sessionUtils;

    @Autowired
    public PermissionsValidator(SessionUtils sessionUtils) {
        this.sessionUtils = sessionUtils;
    }

    // Check if a user is an Admin, if not throws an exception and terminates the request
    public void isAdmin() {
        User loggedUser = sessionUtils.getCurrentUser();
        if (loggedUser.getType() != User.UserType.ADMIN) throw new UnauthorizedException("User is not admin");
    }

    public boolean checkAdmin(){
        User loggedUser = sessionUtils.getCurrentUser();
        return (loggedUser.getType().equals(User.UserType.ADMIN));
    }
}