package alicestudios.EmailService.dtos.user;

import alicestudios.EmailService.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReduceUserDto {

    private String username;

    private int elo;

    public static ReduceUserDto toDto(User user) {
        return new ReduceUserDto(user.getUsername(), user.getPlayer().getElo());
    }
}
