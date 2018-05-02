package supervision.server.license;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LicenseRepository extends JpaRepository<License, Long>, JpaSpecificationExecutor<License> {

	@Query("SELECT c.license FROM Customer c WHERE c.id = :customerId")
	License findByCustomerId(Long customerId);

}
