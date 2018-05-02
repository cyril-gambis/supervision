package supervision.server.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer>, CustomerRepositoryCustom {
	
//	@Query("SELECT ul FROM UsageLog ul WHERE ul.usageLogPage.id = 2 AND datediff(curdate(),ul.date)<40")
	@Query(nativeQuery = true, value = "select * from USAGE_LOG ul inner join user u on ul.user_id = u.id "
			+ "where ul.USAGE_LOG_PAGE_FK = 2 AND "
			+ "ul.DATE BETWEEN CURDATE() - INTERVAL 35 DAY AND CURDATE() ORDER BY ul.DATE desc LIMIT 100")
	List<Customer> findTop100OverviewLogsOrderByDateDesc();

	@Query("SELECT ul FROM UsageLog ul WHERE ul.usageLogPage.id = 2 AND datediff(curdate(),ul.date)<40 "
			+ "AND ul.user.id = :userId")
	List<Customer> findRecentOverviewLogsByUserId(Long userId);

}
