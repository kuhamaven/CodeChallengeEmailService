package EmailService;

import EmailService.dtos.UserRegisterDto;
import EmailService.exceptions.BadRequestException;
import EmailService.repositories.UserRepository;
import EmailService.repositories.VerificationRepository;
import EmailService.services.UserService;
import EmailService.services.VerificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {
    @Mock
    private UserService userService;

    @Mock
    private VerificationService verificationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeAll
    void setUp(){
        verificationService = new VerificationService(verificationRepository,userRepository);
        userService = new UserService(userRepository,passwordEncoder,verificationService);
    }

    @Test @Transactional
    public void test001_validUserShouldRegister(){
        UserRegisterDto dto = new UserRegisterDto("test@email.com","testuser","password123");
        userService.registerUser(dto);
        Assertions.assertTrue(userService.usernameExists(dto.getUsername()));
    }

    @Test @Transactional
    public void test002_invalidUserShouldntRegister(){
        UserRegisterDto dto = new UserRegisterDto("invalidemail","invalid","password123");
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.registerUser(dto);
        });
    }

    @Test @Transactional
    public void test003_takenUsernameShouldntRegister(){
        UserRegisterDto dto = new UserRegisterDto("test@email.com","testuser","password123");
        userService.registerUser(dto);
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.registerUser(dto);
        });
    }
}
