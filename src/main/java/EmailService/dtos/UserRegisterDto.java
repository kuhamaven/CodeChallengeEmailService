package EmailService.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
