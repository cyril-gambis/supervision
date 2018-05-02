package supervision.server.customer;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;

import supervision.server.reporting.command.ReportCommand;
import supervision.server.reporting.command.SearchCriteria;
import supervision.server.reporting.specification.ReportSpecification;
import supervision.server.reporting.specification.ReportSpecificationHelper;
import supervision.server.reporting.specification.SpecificationBuilder;
import supervision.server.userAccount.UserAccountRepository;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Override
	public List<Customer> searchCustomers(@RequestBody ReportCommand command) {
		
		List<Customer> items = ReportSpecificationHelper.searchPagedItems(command, new SpecificationBuilder<Customer>() {

			@Override
			protected Specification<Customer> getSpecificRestrictionPredicate() {
				return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.and();
			}

			@Override
			protected Specification<Customer> getPlanzoneRestrictionPredicate(List<Long> planzoneAuthorizedIds) {
				return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb.and();
			}
			
			@Override
			protected Specification<Customer> createSpecification(SearchCriteria criteria) {
				return new ReportSpecification<>(criteria);
			}
			
		}, customerRepository);

		return items;
	}

	
	/*
	@Override
	public List<Customer> findRecentOverviewLogsByCustomerId(Long customerId) {
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
	*/
	
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
	/*
	private List<Customer> wrap(List<UsageLog> usageLogs) {
		
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
	*/
}
