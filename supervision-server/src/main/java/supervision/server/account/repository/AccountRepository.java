package supervision.server.account.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import supervision.server.account.Account;

@RepositoryRestResource
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

	public Account findByUsername(String username);

}
