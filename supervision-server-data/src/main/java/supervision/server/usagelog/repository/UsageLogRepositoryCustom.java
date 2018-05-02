package supervision.server.usagelog.repository;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import supervision.server.reporting.command.ReportCommand;
import supervision.server.usagelog.UsageLog;
import supervision.server.usagelog.UsageLogFull;

public interface UsageLogRepositoryCustom {

	List<UsageLogFull> searchUsageLogs(@RequestBody ReportCommand command);

	List<UsageLogFull> findRecentOverviewLogsByCustomerId(Long customerId);

	UsageLog findLastOverviewLogByUserId(Long userId);

	List<UsageLogFull> findLastOverviewLogsByCustomerId(Long customerId);

}
