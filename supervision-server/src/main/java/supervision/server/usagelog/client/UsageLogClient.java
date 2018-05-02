package supervision.server.usagelog.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import supervision.server.usagelog.UsageLogCountByUser;
import supervision.server.usagelog.UsageLogCountByUserByMonth;
import supervision.server.usagelog.UsageLogFull;

@FeignClient("SUPERVISION-DATA/api/v1.0")
public interface UsageLogClient {

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/findFirstByUserIdAndUsageLogPageIdOrderByDateDesc?projection=usagelogfullprojection",
			consumes = "application/json")
	UsageLogFull findFirstByUserIdAndUsageLogPageIdOrderByDateDesc(@RequestParam Long userId, @RequestParam Long usageLogPageId);
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/findLastLogByCustomerIdAndUsageLogPageId?projection=usagelogfullprojection",
			consumes = "application/json")	
	Resources<UsageLogFull> findLastLogByCustomerIdAndUsageLogPageId(@RequestParam Long customerId, @RequestParam Long usageLogPageId);

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/countByCustomerIdAndUsageLogPageId",
			consumes = "application/json")
	List<UsageLogCountByUser> countByCustomerIdAndUsageLogPageId(@RequestParam Long customerId, @RequestParam Long usageLogPageId);

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/countByCustomerIdAndUsageLogPageIdGroupByMonth",
			consumes = "application/json")
	List<UsageLogCountByUserByMonth> countByCustomerIdAndUsageLogPageIdGroupByMonth(@RequestParam Long customerId, @RequestParam Long usageLogPageId);

/*	
	@RequestMapping(method = RequestMethod.GET, value = "/users/search/findByPrimaryEmailAddress", consumes = "application/json")
	User findByPrimaryEmailAddress(@RequestParam String emailAddress);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}", consumes = "application/hal+json")
	User findById(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users", consumes = "application/json")
	List<User> findAll();

	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}?projection=usermail", consumes = "application/json")
	User findByIdWithMail(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/search/findByCustomerId", consumes = "application/json")
	List<User> findByCustomerId(@RequestParam Long customerId);
*/
}
