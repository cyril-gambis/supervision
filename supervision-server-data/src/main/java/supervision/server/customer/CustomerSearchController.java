package supervision.server.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import supervision.server.reporting.command.ReportCommand;

@RestController
public class CustomerSearchController {

	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping("/searchCustomers")
	@ResponseBody
	public List<Customer> searchUsageLogs(@RequestBody ReportCommand command) {
		return customerRepository.searchCustomers(command);
	}
/*
	@GetMapping("/findRecentOverviewLogs")
	public List<UsageLogFull> findRecentOverviewLogs(Long customerId) {
		return usageLogRepository.findRecentOverviewLogsByCustomerId(customerId);
	}
	*/
}
