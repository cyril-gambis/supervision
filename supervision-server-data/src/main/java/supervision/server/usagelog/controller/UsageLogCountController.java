package supervision.server.usagelog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import supervision.server.usagelog.UsageLogCountByUser;
import supervision.server.usagelog.UsageLogCountByUserByMonth;
import supervision.server.usagelog.repository.UsageLogRepository;

/**
 * Override method of UsageLogRepository because a RestRepository cannot return something else
 * than the base type (UsageLog) and we want to return UsageLogCountByUser
 * 
 * @date 30/04/2018
 * @author Cyril Gambis
 */
@Controller
public class UsageLogCountController {

	@Autowired
	private UsageLogRepository usageLogRepository;
	
	@ResponseBody
	@GetMapping(value = "/usageLogs/search/countByCustomerIdAndUsageLogPageId",
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<UsageLogCountByUser> count(@RequestParam Long customerId, @RequestParam Long usageLogPageId) {
		return usageLogRepository.countByCustomerIdAndUsageLogPageId(customerId, usageLogPageId);
	}

	@ResponseBody
	@GetMapping(value = "/usageLogs/search/countByCustomerIdAndUsageLogPageIdGroupByMonth",
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<UsageLogCountByUserByMonth> countByMonth(@RequestParam Long customerId, @RequestParam Long usageLogPageId) {
		return usageLogRepository.countByCustomerIdAndUsageLogPageIdGroupByMonth(customerId, usageLogPageId);
	}

}
