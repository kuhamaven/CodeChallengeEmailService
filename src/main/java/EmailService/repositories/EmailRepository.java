package EmailService.repositories;

import EmailService.models.Email;
import EmailService.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends PagingAndSortingRepository<Email, String> {

     List<Email> findAllByUser(User user);
}
