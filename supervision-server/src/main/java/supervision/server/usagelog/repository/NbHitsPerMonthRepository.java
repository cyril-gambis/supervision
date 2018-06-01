package supervision.server.usagelog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import supervision.server.usagelog.CountByMonth;
import supervision.server.usagelog.NbHitsPerMonth;

@RepositoryRestResource(collectionResourceRel = "nbHitsPerMonths", path = "nbHitsPerMonths")
public interface NbHitsPerMonthRepository extends JpaRepository<NbHitsPerMonth, Long>, JpaSpecificationExecutor<NbHitsPerMonth> {
	
	List<NbHitsPerMonth> findByCustomerId(Long customerId);
	List<NbHitsPerMonth> findByUserId(Long userId);

	@Query("SELECT new supervision.server.usagelog.CountByMonth(sum(nb.nbHits), nb.month, nb.year) "
			+ "FROM NbHitsPerMonth nb "
			+ "WHERE nb.customerId = :customerId "
			+ "GROUP BY nb.month, nb.year")
	List<CountByMonth> countByCustomerIdGroupByMonthAndYear(Long customerId);
	
	@Transactional
	Long deleteByUserIdAndMonthAndYearAndPageId(Long userId, Integer month, Integer year, Long pageId);

	@Query("SELECT COALESCE(SUM(nb.nbHits),0) FROM NbHitsPerMonth nb WHERE nb.customerId = :customerId AND nb.pageId = :pageId")
	Long countByCustomerIdAndPageId(Long customerId, Long pageId);

	NbHitsPerMonth findTopByCustomerIdOrderByCalculationDateDesc(Long customerId);

	@Transactional
	Long deleteByCustomerId(Long customerId);

}
