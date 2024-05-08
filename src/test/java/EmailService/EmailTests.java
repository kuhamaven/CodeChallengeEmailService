package EmailService;

import EmailService.dtos.EmailDto;
import EmailService.dtos.UserRegisterDto;
import EmailService.exceptions.BadRequestException;
import EmailService.mail.EmailSender;
import EmailService.models.Email;
import EmailService.models.User;
import EmailService.services.EmailService;
import EmailService.services.MetricsService;
import EmailService.services.UserService;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailTests {
    @Autowired
    private UserService userService;

    @SpyBean
    private EmailService emailService;

    @SpyBean
    private MetricsService metricsService;

    User user;

    @BeforeEach @Transactional
    void beforeEach() {
        UserRegisterDto dto = new UserRegisterDto("test@email.com", "testuser", "password123");
        user = userService.saveUser(dto);
        Mockito.doReturn(user).when(emailService).findLoggedUser();
    }

    @Test @Transactional
    void test001_userShouldBeAbleToSendMail() {
        // Prepare the EmailDto
        EmailDto dto = new EmailDto("subject", "body", "test@email.com");

        // Mock the EmailSender.sendGrid method to do nothing
        try (MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class)) {
            emailSenderMock.when(() -> EmailSender.sendEmail(any(Email.class)))
                    .thenAnswer((Answer<Void>) invocation -> null);

            // Mock the other relevant methods
            Mockito.doCallRealMethod().when(emailService).sendEmail(dto);
            Mockito.doReturn(1).when(metricsService).getEmails();
            Mockito.doReturn(user).when(metricsService).findLoggedUser();

            // Call the sendEmail method
            emailService.sendEmail(dto);

            // Assert the result
            Assertions.assertFalse(user.getSentMails().isEmpty());
        }
    }

    @Test @Transactional
    void test002_userShouldntExceedQuota() {
        // Prepare the EmailDto
        EmailDto dto = new EmailDto("subject", "body", "test@email.com");

        // Mock the EmailSender.sendGrid method to do nothing
        try (MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class)) {
            emailSenderMock.when(() -> EmailSender.sendEmail(any(Email.class)))
                    .thenAnswer((Answer<Void>) invocation -> null);

            // Mock the other relevant methods
            Mockito.doCallRealMethod().when(emailService).sendEmail(dto);
            Mockito.doReturn(1000).when(metricsService).getEmails();

            // Call the sendEmail method and assert exception
            Assertions.assertThrows(BadRequestException.class, () -> {
                emailService.sendEmail(dto);
            });
        }
    }

}
