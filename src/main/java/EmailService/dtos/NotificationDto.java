package alicestudios.EmailService.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private String title;
    private String body;
}