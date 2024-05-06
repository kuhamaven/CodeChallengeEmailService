package alicestudios.EmailService.controllers;

import alicestudios.EmailService.dtos.user.ReduceUserDto;
import alicestudios.EmailService.dtos.user.UserRegisterDto;
import alicestudios.EmailService.services.UserService;
import alicestudios.EmailService.models.User;
import alicestudios.vpet.dtos.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserModel(@PathVariable String id) {
        return userService.findUserById(id);
    }

    @GetMapping()
    public User getUserModel(){
        return userService.findLoggedUser();
    }

    @GetMapping("/UsernameAvailable/{username}")
    public Boolean isUsernameAvailable(@PathVariable String username) {
        return !this.userService.usernameExists(username);
    }

    @GetMapping("/{username}")
    public ReduceUserDto findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/EmailAvailable/{email}")
    public Boolean isEmailAvailable(@PathVariable String email) {
        return !(this.userService.emailExists(email)) && this.userService.emailIsAuthorized(email);
    }

    @PostMapping("/register")
    public void createUser(@RequestBody UserRegisterDto userDto) {
        userService.registerUser(userDto);
    }

    @PutMapping("/token")
    public void saveToken(@RequestBody String token) { userService.registerToken(token);}
}
