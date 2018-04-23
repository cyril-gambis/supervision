package supervision.server.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Query("SELECT u FROM User u WHERE u.primaryEmail.emailAddress = :emailAddress AND u.deleted = 0")
    Optional<User> findByPrimaryEmailAddress(String emailAddress);
    
    List<User> findByFirstName(String firstName);
    
    User findByPrimaryEmailEmailAddress(String emailAddress);
}
