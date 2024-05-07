package EmailService.services;

import EmailService.dtos.UserRegisterDto;
import EmailService.exceptions.BadRequestException;
import EmailService.models.User;
import EmailService.models.VerificationModel;
import EmailService.repositories.UserRepository;
import EmailService.security.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class
UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionUtils sessionUtils;
    private final VerificationService verificationService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, VerificationService verificationService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionUtils = new SessionUtils(userRepository);
        this.verificationService = verificationService;
    }

    public User saveUser(UserRegisterDto userDto) {
        userDto.setUsername(userDto.getUsername().toLowerCase());
        User user = User.fromDto(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return this.userRepository.save(user);
    }

    public User createAdmin(UserRegisterDto userDto){
        userDto.setUsername(userDto.getUsername().toLowerCase());
        User user = User.fromDto(userDto);
        user.setType(User.UserType.ADMIN);
        user.setActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return this.userRepository.save(user);
    }

    public boolean validUser(UserRegisterDto userDto) {
        String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (userDto.getUsername().length() < 3) return false;
        if (userDto.getUsername().length() > 14) return false;
        if (userDto.getPassword().length() < 6) return false;
        return (userDto.getEmail().matches(emailRegex));
    }

    public void registerUser(UserRegisterDto userRegisterDto) {
        userRegisterDto.setUsername(userRegisterDto.getUsername().toLowerCase());
        if (!validUser(userRegisterDto))
            throw new BadRequestException("Email or password are not valid!");
        if (usernameExists(userRegisterDto.getUsername()))
            throw new BadRequestException("Username already taken!");
        if(emailExists(userRegisterDto.getEmail()))
            throw new BadRequestException("Email already taken!");

        User user = saveUser(userRegisterDto);

        VerificationModel verificationModel = new VerificationModel(user);
        verificationService.saveVerification(verificationModel);
    }

    public boolean usernameExists(String userName) {
        return this.userRepository.findByUsername(userName.toLowerCase()).isPresent();
    }

    public boolean emailExists(String email) {
        return this.userRepository.findByEmail(email.toLowerCase()).isPresent();
    }

    public void saveWithoutEncryption(User user) {
        userRepository.save(user);
    }

    public User findLoggedUser() {
        User user = sessionUtils.getCurrentUser();
        return user;
    }
}
