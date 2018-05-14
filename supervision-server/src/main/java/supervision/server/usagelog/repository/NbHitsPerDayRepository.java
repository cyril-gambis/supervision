package supervision.server.usagelog.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import supervision.server.usagelog.NbHitsPerDay;

@RepositoryRestResource(collectionResourceRel = "nbHitsPerDays", path = "nbHitsPerDays")
public interface NbHitsPerDayRepository extends JpaRepository<NbHitsPerDay, Long>, JpaSpecificationExecutor<NbHitsPerDay> {
	
	List<NbHitsPerDay> findByCustomerId(Long customerId);
	List<NbHitsPerDay> findByPageId(Long customerId);
	
	@Transactional
	Long deleteByDayAndMonthAndYearAndCustomerIdAndPageId(Integer day, Integer month, Integer year, Long customerId,Long pageId);

	@Transactional
	Long deleteByCustomerId(Long customerId);

}