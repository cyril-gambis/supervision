package supervision.server.usagelog.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import supervision.server.usagelog.LastAccess;

@RepositoryRestResource(collectionResourceRel = "lastAccesses", path = "lastAccesses")
public interface LastAccessRepository extends JpaRepository<LastAccess, Long>, JpaSpecificationExecutor<LastAccess> {
	
	List<LastAccess> findByCustomerId(Long customerId);

	Optional<LastAccess> findByUserId(Long userId);

	@Transactional
	Long deleteByUserId(Long userId);
	
}
