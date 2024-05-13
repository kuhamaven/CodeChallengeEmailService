package EmailService.controllers;

import EmailService.dtos.UserRegisterDto;
import EmailService.models.User;
import EmailService.services.UserService;
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

    @PostMapping("/register")
    public void createUser(@RequestBody UserRegisterDto userDto) {
        userService.registerUser(userDto);
    }

}
