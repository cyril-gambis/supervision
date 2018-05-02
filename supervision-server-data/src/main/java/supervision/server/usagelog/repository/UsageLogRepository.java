package supervision.server.usagelog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import supervision.server.usagelog.UsageLog;
import supervision.server.usagelog.UsageLogCountByUser;
import supervision.server.usagelog.UsageLogCountByUserByMonth;

@RepositoryRestResource(collectionResourceRel = "usageLogs", path = "usageLogs")
public interface UsageLogRepository extends JpaRepository<UsageLog, Long>, JpaSpecificationExecutor<UsageLog>, UsageLogRepositoryCustom {
	
//	@Query("SELECT ul FROM UsageLog ul WHERE ul.usageLogPage.id = 2 AND datediff(curdate(),ul.date)<40")
	@Query(nativeQuery = true, value = "select * from USAGE_LOG ul inner join user u on ul.user_id = u.id "
			+ "where ul.USAGE_LOG_PAGE_FK = 2 AND "
			+ "ul.DATE BETWEEN CURDATE() - INTERVAL 35 DAY AND CURDATE() ORDER BY ul.DATE desc LIMIT 100")
	List<UsageLog> findTop100OverviewLogsOrderByDateDesc();

	@Query("SELECT ul FROM UsageLog ul WHERE ul.usageLogPage.id = 2 AND datediff(curdate(),ul.date)<40 "
			+ "AND ul.user.id = :userId")
	List<UsageLog> findRecentOverviewLogsByUserId(Long userId);
	
	UsageLog findFirstByUserIdAndUsageLogPageIdOrderByDateDesc(Long userId, Long usageLogPageId);
	
	@Query("SELECT ul FROM UsageLog ul, UserAccount ua WHERE ua.customer.id = :customerId "
			+ "AND ua.user = ul.user AND ul.usageLogPage.id = :usageLogPageId "
			+ "GROUP BY ul.user "
			+ "ORDER BY ul.date DESC")
	List<UsageLog> findLastLogByCustomerIdAndUsageLogPageId(Long customerId, Long usageLogPageId);
		
	@Query("SELECT new supervision.server.usagelog.UsageLogCountByUser(ul.user, count(ul)) "
			+ "FROM UsageLog ul, UserAccount ua "
			+ "WHERE ua.customer.id = :customerId "
				+ "AND ua.user = ul.user AND ul.usageLogPage.id = :usageLogPageId "
			+ "GROUP BY ul.user")
	List<UsageLogCountByUser> countByCustomerIdAndUsageLogPageId(Long customerId, Long usageLogPageId);

	@Query("SELECT new supervision.server.usagelog.UsageLogCountByUserByMonth(ul.user, count(ul), month(ul.date), year(ul.date)) "
			+ "FROM UsageLog ul, UserAccount ua "
			+ "WHERE ua.customer.id = :customerId "
				+ "AND ua.user = ul.user AND ul.usageLogPage.id = :usageLogPageId "
			+ "GROUP BY year(ul.date), month(ul.date), ul.user")
	List<UsageLogCountByUserByMonth> countByCustomerIdAndUsageLogPageIdGroupByMonth(Long customerId, Long usageLogPageId);
	
/*

	@Query("SELECT COUNT(u) FROM User u WHERE u.name=:name")

	Long countByName(String name);
	
	
	

	@Query("SELECT 	Closing_Date = DATEADD(MONTH, DATEDIFF(MONTH, 0, Closing_Date), 0), 
        Category,  
        COUNT(Status) TotalCount 
FROM    MyTable
WHERE   Closing_Date >= '2012-02-01' 
AND     Closing_Date <= '2012-12-31'
AND     Defect_Status1 IS NOT NULL
GROUP BY DATEADD(MONTH, DATEDIFF(MONTH, 0, Closing_Date), 0), Category;")
	List<UsageLog> findCountByCustomerIdAndUsageLogPageIdGroupByMonth(Long customerId, Long usageLogPageId);
*/	
}
