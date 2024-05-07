package EmailService;

import EmailService.dtos.UserRegisterDto;
import EmailService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class OnStart {

    private static final boolean RUN_ON_START = true;

    private final UserService userService;

    @Autowired
    public OnStart(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        if (RUN_ON_START) {
            if (!userService.usernameExists("analogman")) {
                UserRegisterDto admin = new UserRegisterDto("admin@mailservice.com", "admin", System.getenv("ADMIN_PASSWORD"));
                userService.createAdmin(admin);
            }
        }
    }
}