package supervision.server.usagelog;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import supervision.server.reporting.command.ReportCommand;
import supervision.server.reporting.specification.ReportSpecificationHelper;
import supervision.server.reporting.specification.UsageLogSpecificationBuilder;
import supervision.server.user.User;
import supervision.server.userAccount.UserAccount;
import supervision.server.userAccount.UserAccountRepository;

public class UsageLogRepositoryImpl implements UsageLogRepositoryCustom {

	@Autowired
	private UsageLogRepository usageLogRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Override
	public List<UsageLogFull> searchUsageLogs(@RequestBody ReportCommand command) {
		
		List<UsageLogFull> items = wrap(ReportSpecificationHelper.searchAllItems(command, new UsageLogSpecificationBuilder(), usageLogRepository));

		return items;
	}
	
	@Override
	public List<UsageLogFull> findRecentOverviewLogsByCustomerId(Long customerId) {
		// Get Users of this planzone
		List<UserAccount> userAccounts = userAccountRepository.findByCustomerId(customerId);
		
		List<User> users = userAccounts.stream().map(ua -> ua.getUser())
				.filter(Objects::nonNull)
			.collect(Collectors.toList());
		
		return wrap(users.stream()
			.flatMap(u -> {
				List<UsageLog> logs = usageLogRepository.findRecentOverviewLogsByUserId(u.getId());
				if (logs != null) {
					return logs.stream();
				} else {
					return Stream.empty();
				}
			})
			.collect(Collectors.toList())
			);

	}
	
	
/*
	private List<UsageLogFull> wrap(List<UsageLog> usageLogs) {
		return usageLogs.stream().limit(ReportCommand.NB_MAX_RESULTS).map(t -> new UsageLogFull(
				t.getId(), t.getDate(), t.getTargetId(), "url",
				"page", "category",
				"entityName",
				"actiontype",
				"firstName", "lastName", "email",
				null
		)).collect(Collectors.toList());
	}
*/
	
	private List<UsageLogFull> wrap(List<UsageLog> usageLogs) {
		
		return usageLogs.stream().limit(ReportCommand.NB_MAX_RESULTS).map((UsageLog t) -> {
			
			UsageLogPage page = t.getUsageLogPage();
			String categoryDescription = "";
			String entityName = "";
			String pageDescription = "";
			String actionTypeDescription = "";
			Long pageId = 0L;
			if (page != null) {
				pageDescription = page.getDescription();
				pageId = page.getId();
				UserAction action = page.getUserAction();
				if (action != null) {
					actionTypeDescription = action.getActionType().getDescription();
					ActionCategory actionCategory = action.getActionCategory();
					if (actionCategory != null) {
						categoryDescription = actionCategory.getDescription();
						entityName = actionCategory.getEntityName();
					}
				}
			}
			return new UsageLogFull(
					t.getId(), pageId, t.getDate(), t.getTargetId(), page.getUrl(),
					pageDescription,
					categoryDescription,
					entityName,
					actionTypeDescription,
					t.getUser().getFirstName(), t.getUser().getLastName(), t.getUser().getPrimaryEmail().getEmailAddress(),
					t.getUser());									
		}).collect(Collectors.toList());
	}
	
}
