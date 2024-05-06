package EmailService.repositories;


import EmailService.models.User;
import EmailService.models.VerificationModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface VerificationRepository extends PagingAndSortingRepository<VerificationModel, Integer> {

    Optional<VerificationModel> findByToken(String token);

    Optional<VerificationModel> findByUser(User user);
}
