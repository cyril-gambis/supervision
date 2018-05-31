package supervision.server.usagelog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import supervision.server.reporting.command.ReportCommand;
import supervision.server.usagelog.UsageLogFull;
import supervision.server.usagelog.repository.UsageLogRepository;

@RepositoryRestController
@RequestMapping("/usageLogs")
public class UsageLogSearchController {

	@Autowired
	private UsageLogRepository usageLogRepository;

	@PostMapping("/search/searchUsageLogs")
	@ResponseBody
	public ResponseEntity<Resources<UsageLogFull>> searchUsageLogs(@RequestBody ReportCommand command) {
		return ResponseEntity.ok().body(new Resources<>(usageLogRepository.searchUsageLogs(command)));
	}

	@GetMapping("/search/findLastUsageLogsByCustomerId")
	public ResponseEntity<Resources<UsageLogFull>> findLastUsageLogsByCustomerId(Long customerId) {
		return ResponseEntity.ok().body(new Resources<>(usageLogRepository.findLastUsageLogsByCustomerId(customerId)));
	}

	@GetMapping("/search/findLastOverviewLogsByCustomerId")
	public ResponseEntity<Resources<UsageLogFull>> findLastOverviewLogsByCustomerId(Long customerId) {
		return ResponseEntity.ok().body(new Resources<>(usageLogRepository.findLastOverviewLogsByCustomerId(customerId)));
	}

	@GetMapping("/search/findLastByCustomerIdGroupByUser")
	public ResponseEntity<Resources<UsageLogFull>> findLastByCustomerIdGroupByUser(Long customerId) {
		List<UsageLogFull> logs = usageLogRepository.findLastByCustomerIdGroupByUser(customerId);
		return ResponseEntity.ok().body(new Resources<>(logs));
	}
}
