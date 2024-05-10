package EmailService;

import EmailService.dtos.UserRegisterDto;
import EmailService.exceptions.BadRequestException;
import EmailService.mail.EmailSender;
import EmailService.models.Email;
import EmailService.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserTests {
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void test001_validUserShouldRegister() {
        // Prevent the API from emailing the test user
        try (MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class)) {
            emailSenderMock.when(() -> EmailSender.sendEmail(any(Email.class)))
                    .thenAnswer((Answer<Void>) invocation -> null);

            // Prepare the user
            UserRegisterDto dto = new UserRegisterDto("test@email.com", "testuser", "password123");

            // Call the register method
            userService.registerUser(dto);

            // Assert the result
            Assertions.assertTrue(userService.usernameExists(dto.getUsername()));
        }
    }

    @Test
    @Transactional
    public void test002_invalidUserShouldntRegister() {
        // Prepare the user
        UserRegisterDto dto = new UserRegisterDto("invalidemail", "invalid", "password123");

        // Call the register method and assert it throws the right exception
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.registerUser(dto);
        });
    }

    @Test
    @Transactional
    public void test003_takenUsernameShouldntRegister() {
        // Prevent the API from emailing the test user
        try (MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class)) {
            emailSenderMock.when(() -> EmailSender.sendEmail(any(Email.class)))
                    .thenAnswer((Answer<Void>) invocation -> null);

            // Prepare the user
            UserRegisterDto dto = new UserRegisterDto("test@email.com", "testuser", "password123");

            // Call the register method
            userService.registerUser(dto);

            // Call the register method again, and assert it throws the right exception
            Assertions.assertThrows(BadRequestException.class, () -> {
                userService.registerUser(dto);
            });
        }
    }
}
