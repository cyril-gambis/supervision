package supervision.server.customer;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import supervision.server.reporting.command.ReportCommand;

public interface CustomerRepositoryCustom {

	List<Customer> searchCustomers(@RequestBody ReportCommand command);

	//List<Customer> findRecentOverviewLogsByCustomerId(Long customerId);

}
