package supervision.server.usagelog.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import supervision.server.reporting.command.ReportCommand;
import supervision.server.reporting.specification.ReportSpecificationHelper;
import supervision.server.reporting.specification.UsageLogSpecificationBuilder;
import supervision.server.usagelog.ActionCategory;
import supervision.server.usagelog.UsageLog;
import supervision.server.usagelog.UsageLogFull;
import supervision.server.usagelog.UsageLogPage;
import supervision.server.usagelog.UserAction;
import supervision.server.user.User;
import supervision.server.user.UserRepository;
import supervision.server.userAccount.UserAccount;
import supervision.server.userAccount.UserAccountRepository;

public class UsageLogRepositoryImpl implements UsageLogRepositoryCustom {

	@Autowired
	private UsageLogRepository usageLogRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UsageLogFull> searchUsageLogs(@RequestBody ReportCommand command) {
		
		List<UsageLogFull> items = wrap(ReportSpecificationHelper.searchAllItems(command, new UsageLogSpecificationBuilder(), usageLogRepository));

		return items;
	}
	
	@Override
	public List<UsageLogFull> findLastUsageLogsByCustomerId(Long customerId) {
		// Get Users of this planzone
		List<UserAccount> userAccounts = userAccountRepository.findByCustomerId(customerId);
		
		List<User> users = userAccounts.stream().map(ua -> ua.getUser())
				.filter(Objects::nonNull)
			.collect(Collectors.toList());
		
		return wrap(users.stream()
			.flatMap(u -> {
				List<UsageLog> logs = usageLogRepository.findTop100ByUserIdOrderByDateDesc(u.getId());
				if (logs != null) {
					return logs.stream();
				} else {
					return Stream.empty();
				}
			})
			.collect(Collectors.toList())
			);

	}
	
	@Override
	public List<UsageLogFull> findLastOverviewLogsByCustomerId(Long customerId) {
		// Get Users of this planzone
		List<UserAccount> userAccounts = userAccountRepository.findByCustomerId(customerId);
		
		List<User> users = userAccounts.stream().map(ua -> ua.getUser())
				.filter(Objects::nonNull)
			.collect(Collectors.toList());
		
		List<UsageLog> logs = users.stream()
			.flatMap(u -> {
				UsageLog log = usageLogRepository.findLastOverviewLogByUserId(u.getId());
				if (log == null) {
					return Stream.empty();
				} else {
					return Arrays.asList(log).stream();
				}
			})
			.collect(Collectors.toList());

		List<UsageLogFull> result = wrap(logs);
		return result;
	}
	
	Long USAGE_LOG_PAGE_ID_OVERVIEW = 2L;
	
	@Override
	public UsageLog findLastOverviewLogByUserId(Long userId) {
		// Get Users of this planzone
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			UsageLog usageLog = usageLogRepository.findFirstByUserIdAndUsageLogPageIdOrderByDateDesc(
					user.get().getId(),
					USAGE_LOG_PAGE_ID_OVERVIEW
					);
			return usageLog;
		}
		return null;
	}

	@Override
	public List<UsageLogFull> findLastByCustomerIdGroupByUser(Long customerId) {

		List<User> usersByCustomerId = userRepository.findByCustomerId(customerId);
		
		List<UsageLog> logs = new ArrayList<UsageLog>();
		for (User user : usersByCustomerId) {
			UsageLog log = usageLogRepository.findFirstByUserIdOrderByDateDesc(user.getId());
			if (log != null) {
				logs.add(log);
			}
		}
		
		return wrap(logs);
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
