package supervision.server.usagelog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import supervision.server.usagelog.CountByDay;
import supervision.server.usagelog.CountByUser;
import supervision.server.usagelog.CountByUserByMonth;
import supervision.server.usagelog.UsageLog;

@RepositoryRestResource(collectionResourceRel = "usageLogs", path = "usageLogs")
public interface UsageLogRepository extends JpaRepository<UsageLog, Long>, JpaSpecificationExecutor<UsageLog>, UsageLogRepositoryCustom {
	
//	@Query("SELECT ul FROM UsageLog ul WHERE ul.usageLogPage.id = 2 AND datediff(curdate(),ul.date)<40")
	@Query(nativeQuery = true, value = "select * from USAGE_LOG ul inner join user u on ul.user_id = u.id "
			+ "where ul.USAGE_LOG_PAGE_FK = 2 AND "
			+ "ul.DATE BETWEEN CURDATE() - INTERVAL 35 DAY AND CURDATE() ORDER BY ul.DATE desc LIMIT 100")
	List<UsageLog> findTop100OverviewLogsOrderByDateDesc();

//	@Query("SELECT ul FROM UsageLog ul WHERE datediff(curdate(),ul.date)<40 AND ul.user.id = :userId")
	List<UsageLog> findTop100ByUserIdOrderByDateDesc(Long userId);
	
	UsageLog findFirstByUserIdAndUsageLogPageIdOrderByDateDesc(Long userId, Long usageLogPageId);

	UsageLog findFirstByUserIdOrderByDateDesc(Long userId);

	@Query("SELECT ul FROM UsageLog ul, UserAccount ua WHERE ua.customer.id = :customerId "
			+ "AND ua.user = ul.user AND ul.usageLogPage.id = :usageLogPageId "
			+ "GROUP BY ul.user "
			+ "ORDER BY ul.date DESC")
	List<UsageLog> findLastLogByCustomerIdAndUsageLogPageId(Long customerId, Long usageLogPageId);

	@Query(nativeQuery = true,  value=
			"SELECT * FROM "
			+ "(select ul.id, USER_ID as userid, max(ul.date) as maxdate "
			+ "from USAGE_LOG ul, USER_ACCOUNT ua WHERE ua.CUSTOMER_FK = :customerId "
			+ "AND ua.USER_FK = ul.USER_ID "
			+ "GROUP BY USER_ID) r "
			+ "INNER JOIN USAGE_LOG ul2 ON ul2.USER_ID = r.userid AND ul2.date = r.maxdate "
			+ "group by user_id order by date desc")
	List<UsageLog> findLastLogByCustomerId(Long customerId);
	
	@Query("SELECT new supervision.server.usagelog.CountByUser(ul.user, count(ul)) "
			+ "FROM UsageLog ul, UserAccount ua "
			+ "WHERE ua.customer.id = :customerId "
				+ "AND ua.user = ul.user AND ul.usageLogPage.id = :usageLogPageId "
			+ "GROUP BY ul.user")
	List<CountByUser> countByCustomerIdAndUsageLogPageId(Long customerId, Long usageLogPageId);

	@Query("SELECT new supervision.server.usagelog.CountByUserByMonth(ul.user, count(ul), month(ul.date), year(ul.date)) "
			+ "FROM UsageLog ul, UserAccount ua "
			+ "WHERE ua.customer.id = :customerId "
				+ "AND ua.user = ul.user AND ul.usageLogPage.id = :usageLogPageId "
			+ "GROUP BY year(ul.date), month(ul.date), ul.user")
	List<CountByUserByMonth> countByCustomerIdAndUsageLogPageIdGroupByMonth(Long customerId, Long usageLogPageId);

	@Query("SELECT new supervision.server.usagelog.CountByUserByMonth(ul.user, count(ul), month(ul.date), year(ul.date)) "
			+ "FROM UsageLog ul, UserAccount ua "
			+ "WHERE ua.customer.id = :customerId "
				+ "AND ua.user = ul.user "
			+ "GROUP BY year(ul.date), month(ul.date), ul.user")
	List<CountByUserByMonth> countByCustomerIdGroupByMonth(Long customerId);
	
	@Query("SELECT new supervision.server.usagelog.CountByDay(day(ul.date), month(ul.date), year(ul.date), count(ul)) "
			+ "FROM UsageLog ul, UserAccount ua "
			+ "WHERE ua.customer.id = :customerId "
				+ "AND ua.user = ul.user AND ul.usageLogPage.id = :usageLogPageId "
			+ "GROUP BY year(ul.date), month(ul.date), day(ul.date)")
	List<CountByDay> countByCustomerIdAndUsageLogPageIdGroupByDay(Long customerId, Long usageLogPageId);
	
	
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
