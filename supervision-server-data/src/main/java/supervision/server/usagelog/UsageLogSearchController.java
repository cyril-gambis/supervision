package supervision.server.usagelog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import supervision.server.reporting.command.ReportCommand;

@RestController
public class UsageLogSearchController {

	@Autowired
	private UsageLogRepository usageLogRepository;

	@PostMapping("/searchUsageLogs")
	@ResponseBody
	public List<UsageLogFull> searchUsageLogs(@RequestBody ReportCommand command) {
		return usageLogRepository.searchUsageLogs(command);
	}

	@GetMapping("/findRecentOverviewLogs")
	public List<UsageLogFull> findRecentOverviewLogs(Long customerId) {
		return usageLogRepository.findRecentOverviewLogsByCustomerId(customerId);
	}
	
}
