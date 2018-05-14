package supervision.server.usagelog.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import supervision.server.usagelog.CountByDay;
import supervision.server.usagelog.CountByUser;
import supervision.server.usagelog.CountByUserByMonth;
import supervision.server.usagelog.UsageLog;
import supervision.server.usagelog.UsageLogFull;

@FeignClient("SUPERVISION-DATA/api/v1.0")
public interface UsageLogClient {

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/findFirstByUserIdAndUsageLogPageIdOrderByDateDesc",
			consumes = "application/json")
	UsageLog findFirstByUserIdAndUsageLogPageIdOrderByDateDesc(@RequestParam Long userId, @RequestParam Long usageLogPageId);
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/findLastLogByCustomerIdAndUsageLogPageId",
			consumes = "application/json")	
	Resources<UsageLog> findLastLogByCustomerIdAndUsageLogPageId(@RequestParam Long customerId, @RequestParam Long usageLogPageId);

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/findLastByCustomerIdGroupByUser",
			consumes = "application/json")
	Resources<UsageLogFull> findLastByCustomerIdGroupByUser(@RequestParam Long customerId);

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/countByCustomerIdAndUsageLogPageId",
			consumes = "application/json")
	List<CountByUser> countByCustomerIdAndUsageLogPageId(@RequestParam Long customerId, @RequestParam Long usageLogPageId);

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/countByCustomerIdAndUsageLogPageIdGroupByMonth",
			consumes = "application/json")
	List<CountByUserByMonth> countByCustomerIdAndUsageLogPageIdGroupByMonth(@RequestParam Long customerId, @RequestParam Long usageLogPageId);

	@RequestMapping(method = RequestMethod.GET,
			value = "/usageLogs/search/countByCustomerIdGroupByMonth",
			consumes = "application/json")
	List<CountByUserByMonth> countByCustomerIdGroupByMonth(@RequestParam Long customerId);
	
	@GetMapping(value = "/usageLogs/search/countByCustomerIdAndUsageLogPageIdGroupByDay",
			consumes = "application/json")
	List<CountByDay> countByCustomerIdAndUsageLogPageIdGroupByDay(@RequestParam Long cutomerId, @RequestParam Long usageLogPageId);
	
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
