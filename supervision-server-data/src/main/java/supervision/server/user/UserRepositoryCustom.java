package supervision.server.user;

import java.util.Optional;

public interface UserRepositoryCustom {

	public Optional<User> findByUsername(String username);

}