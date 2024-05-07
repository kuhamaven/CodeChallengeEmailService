package EmailService;

import EmailService.dtos.EmailDto;
import EmailService.dtos.UserRegisterDto;
import EmailService.mail.EmailSender;
import EmailService.models.Email;
import EmailService.models.User;
import EmailService.repositories.DailyMetricsRepository;
import EmailService.repositories.EmailRepository;
import EmailService.repositories.UserRepository;
import EmailService.repositories.VerificationRepository;
import EmailService.security.UserAuth;
import EmailService.services.EmailService;
import EmailService.services.MetricsService;
import EmailService.services.UserService;
import EmailService.services.VerificationService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailTests {
    @Mock
    private UserService userService;

    @Mock
    private VerificationService verificationService;

    @Mock
    private EmailService emailService;

    @Mock
    private MetricsService metricsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DailyMetricsRepository dailyMetricsRepository;

    @Autowired
    private EmailRepository emailRepository;

    Authentication authentication;
    SecurityContext securityContext;
    User user;

    @BeforeAll
    void setUp(){
        metricsService = new MetricsService(dailyMetricsRepository,userRepository);
        verificationService = new VerificationService(verificationRepository,userRepository);
        userService = new UserService(userRepository,passwordEncoder,verificationService);
        emailService = new EmailService(userRepository,emailRepository,metricsService);
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);
    }

    @BeforeEach
    void beforeEach(){
        UserRegisterDto dto = new UserRegisterDto("test@email.com","testuser","password123");
        user = userService.saveUser(dto);
        setSecurityContext(user);
    }

    private void setSecurityContext(User user){
        UserAuth userAuth = new UserAuth(user.getUsername(), user.getPassword());
        Mockito.when(authentication.getPrincipal()).thenReturn();
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test @Transactional
    public void test001_userShouldBeAbleToSendMail(){
        EmailDto dto = new EmailDto("subject","body","test@email.com");

        try (MockedStatic<EmailSender> emailSenderMock = Mockito.mockStatic(EmailSender.class)) {
            emailSenderMock.when(() -> EmailSender.sendGrid(any(Email.class)))
                    .thenAnswer((Answer<Void>) invocation -> null);

            emailService.sendEmail(dto);

            Assertions.assertFalse(emailService.findLoggedUser().getSentMails().isEmpty());
        }
    }

}
