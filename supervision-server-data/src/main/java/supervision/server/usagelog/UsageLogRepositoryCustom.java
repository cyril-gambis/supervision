package supervision.server.usagelog;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import supervision.server.reporting.command.ReportCommand;

public interface UsageLogRepositoryCustom {

	List<UsageLogFull> searchUsageLogs(@RequestBody ReportCommand command);

	List<UsageLogFull> findRecentOverviewLogsByCustomerId(Long customerId);

}
