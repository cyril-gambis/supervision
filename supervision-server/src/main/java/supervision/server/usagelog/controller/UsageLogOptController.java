package supervision.server.usagelog.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import supervision.server.usagelog.CountByDay;
import supervision.server.usagelog.CountByMonth;
import supervision.server.usagelog.CountByUserByMonth;
import supervision.server.usagelog.LastAccess;
import supervision.server.usagelog.NbHitsPerDay;
import supervision.server.usagelog.NbHitsPerMonth;
import supervision.server.usagelog.UsageLogFull;
import supervision.server.usagelog.client.UsageLogClient;
import supervision.server.usagelog.repository.LastAccessRepository;
import supervision.server.usagelog.repository.NbHitsPerDayRepository;
import supervision.server.usagelog.repository.NbHitsPerMonthRepository;
import supervision.server.user.client.UserClient;

/**
 * IMPORTANT: if you use @RepositoryRestController, you must put a @RequestMapping,
 * otherwise your controllers will not be created and accessing them will result in Error 404
 * 
 * @date 16/04/2018
 * @author Cyril Gambis
 */
@RepositoryRestController
@RequestMapping(path="/usageLogOpt-custom")
public class UsageLogOptController {

	@Autowired
	private LastAccessRepository lastAccessRepository;
	
	@Autowired
	private NbHitsPerMonthRepository nbHitsPerMonthRepository;

	@Autowired
	private NbHitsPerDayRepository nbHitsPerDayRepository;

	@Autowired
	private UserClient userClient;
	
	@Autowired
	private UsageLogClient usageLogClient;
	
	private static final Long OVERVIEW_PAGE_ID = 2L;
	private static final Long PROJECTS_PAGE_ID = 3L;
	private static final Long PZ_TASKS_PAGE_ID = 4L;
	private static final Long PZ_WORKLOAD_PAGE_ID = 5L;
	private static final Long PZ_SCHEDULE_PAGE_ID = 6L;
	private static final Long PZ_TIMESHEET_PAGE_ID = 7L;
	
	private static final Long PZ_DISCUSSION_ID = 24L;
	

	private static final Long PR_OVERVIEW_PAGE_ID = 13L;
	private static final Long PR_TASKS_PAGE_ID = 8L;
	private static final Long PR_WORKLOAD_PAGE_ID = 22L;
	private static final Long PR_SCHEDULE_PAGE_ID = 15L;
	private static final Long PR_TIMESHEET_PAGE_ID = 23L;

	private static final Long PR_DISCUSSION_ID = 28L;
	
	private static final Long CALENDAR_PAGE_ID = 14L;
	
	private static final Long REPORTS_ID = 19L;
	private static final Long TEMPLATES_ID = 20L;
	private static final Long EXPORTS_ID = 21L;
	
	private static final Long MESSAGE_ON_TASK_ID = 30L;
	private static final Long MESSAGE_ON_MILESTONE_ID = 31L;
	private static final Long MESSAGE_ON_EVENT_ID = 32L;
	private static final Long MESSAGE_POPUP_ID = 33L;
	
	
	/*
	 * Version non optimisée
	 *
	@GetMapping(value="/computeLastAccesses")
	@ResponseBody
	public ResponseEntity<String> computeLastAccesses(@RequestParam Long customerId) {
		
		// Get all users of this customer
		Resources<User> users = userClient.findByCustomerId(customerId);
		
		// For each, get last access
		if (users.getContent().size() > 0) {
			for (User user : users) {

				// Delete previous last access if present
				lastAccessRepository.deleteByUserId(user.getId());
				
				Optional<UsageLogFull> log = Optional.empty();
				try {
					log = Optional.ofNullable(
							usageLogClient.findFirstByUserIdAndUsageLogPageIdOrderByDateDesc(user.getId(), OVERVIEW_PAGE_ID)
					);
				} catch (FeignClientException e) {
					if (e.getStatus() != 404) {
						throw e;
					}
				}
				
				if (log.isPresent()) {
					lastAccessRepository.save(
							new LastAccess(customerId, user.getId(), log.get().getDate(),
									LocalDateTime.now(), user.getFirstName(), user.getLastName()));
				} else {
					lastAccessRepository.save(
							new LastAccess(customerId, user.getId(), null, LocalDateTime.now(),
									user.getFirstName(), user.getLastName()));
				}
			}
			return ResponseEntity.ok("Compute completed on " + users.getContent().size() + " users.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}*/

	/**
	 * Get last access for all users of this customer.
	 * Note: if there is no last access for a client (never connected), will not send values for this client
	 * 
	 * @date 27/04/2018
	 * @author Cyril Gambis
	 * @param customerId
	 * @return
	 */
	@GetMapping(value="/computeLastAccesses")
	@ResponseBody
	public ResponseEntity<String> computeLastAccesses(@RequestParam Long customerId) {

		Resources<UsageLogFull> logs = usageLogClient.findLastByCustomerIdGroupByUser(customerId);
		
		for (UsageLogFull log : logs.getContent()) {
			// Delete previous last access if present
			lastAccessRepository.deleteByUserId(log.getUser().getId());
			
			lastAccessRepository.save(
					new LastAccess(customerId, log.getUser().getId(), log.getDate(),
							LocalDateTime.now(), log.getUser().getFirstName(), log.getUser().getLastName()));			
		}

		return ResponseEntity.ok("Compute completed on " + logs.getContent().size() + " users.");
	}
	
	@GetMapping(value="/computeAllAccessesAllPages")
	@ResponseBody
	public ResponseEntity<String> computeAllAccessesAllPages(@RequestParam Long customerId) {
		
		List<Long> pageIds = Arrays.asList(OVERVIEW_PAGE_ID, PROJECTS_PAGE_ID, PZ_TASKS_PAGE_ID, PZ_WORKLOAD_PAGE_ID,
				PZ_SCHEDULE_PAGE_ID, PZ_TIMESHEET_PAGE_ID, PZ_DISCUSSION_ID,
				PR_OVERVIEW_PAGE_ID, PR_TASKS_PAGE_ID, PR_WORKLOAD_PAGE_ID, PR_SCHEDULE_PAGE_ID, PR_TIMESHEET_PAGE_ID,
				PR_DISCUSSION_ID, CALENDAR_PAGE_ID, REPORTS_ID, TEMPLATES_ID, EXPORTS_ID, MESSAGE_ON_TASK_ID,
				MESSAGE_ON_MILESTONE_ID, MESSAGE_ON_EVENT_ID, MESSAGE_POPUP_ID);

		int nbLinesComputed = 0;
		
		// reset nb hits per month repository for this customer
		nbHitsPerDayRepository.deleteByCustomerId(customerId);
		
		for (Long id : pageIds) {
			List<CountByUserByMonth> countByUser =
					usageLogClient.countByCustomerIdAndUsageLogPageIdGroupByMonth(customerId, id);
			
			for (CountByUserByMonth count : countByUser) {
				
				// Delete previous entry
// Not used anymore, we bulk delete at the start of the method				
//				nbHitsPerMonthRepository.deleteByUserIdAndMonthAndYearAndPageId(count.getUser().getId(), count.getMonth(), count.getYear(), id);
				
				nbHitsPerMonthRepository.save(new NbHitsPerMonth(customerId, count.getUser().getId(), count.getMonth(),
						count.getYear(), count.getCount(), id, LocalDateTime.now()));
				
			}
			nbLinesComputed += countByUser.size();
		}
		return ResponseEntity.ok("Compute completed on " + nbLinesComputed + " lines.");
	}
	
	
	
	@GetMapping(value="/getCountByMonth")
	@ResponseBody
	public List<CountByMonth> getCountByMonth(@RequestParam Long customerId) {
		List<CountByMonth> hits = countByCustomerIdGroupByMonthAndYear(customerId);
		return hits;
	}
	
	@GetMapping(value="nbHitsPerMonths/search/countByCustomerIdGroupByMonthAndYear",
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<CountByMonth> countByCustomerIdGroupByMonthAndYear(@RequestParam Long customerId) {
		return nbHitsPerMonthRepository.countByCustomerIdGroupByMonthAndYear(customerId);
	}
	
	
	/**
	 * Compute the number of hits by page and by day
	 * Don't take into account the user
	 * 
	 * @date 03/05/2018
	 * @author Cyril Gambis
	 * @param customerId
	 * @return
	 */
	@GetMapping(value="/computeOverviewAccessesByDay")
	@ResponseBody
	public ResponseEntity<String> computeOverviewAccessesByDay(@RequestParam Long customerId) {
	/*	
		List<Long> pageIds = Arrays.asList(OVERVIEW_PAGE_ID, PROJECTS_PAGE_ID, PZ_TASKS_PAGE_ID, PZ_WORKLOAD_PAGE_ID,
				PZ_SCHEDULE_PAGE_ID, PZ_TIMESHEET_PAGE_ID, PZ_DISCUSSION_ID,
				PR_OVERVIEW_PAGE_ID, PR_TASKS_PAGE_ID, PR_WORKLOAD_PAGE_ID, PR_SCHEDULE_PAGE_ID, PR_TIMESHEET_PAGE_ID,
				PR_DISCUSSION_ID, CALENDAR_PAGE_ID, REPORTS_ID, TEMPLATES_ID, EXPORTS_ID, MESSAGE_ON_TASK_ID,
				MESSAGE_ON_MILESTONE_ID, MESSAGE_ON_EVENT_ID, MESSAGE_POPUP_ID);
*/
		List<Long> pageIds = Arrays.asList(OVERVIEW_PAGE_ID);
		
		int nbLinesComputed = 0;
		
		for (Long id : pageIds) {
			List<CountByDay> countByDay =
					usageLogClient.countByCustomerIdAndUsageLogPageIdGroupByDay(customerId, id);
			
			for (CountByDay count : countByDay) {
				
				// Delete previous entry
				nbHitsPerDayRepository.deleteByDayAndMonthAndYearAndCustomerIdAndPageId(count.getDay(), count.getMonth(), count.getYear(), customerId, id);
				
				nbHitsPerDayRepository.save(new NbHitsPerDay(customerId, count.getDay(), count.getMonth(), count.getYear(), count.getCount(),
						id, LocalDateTime.now()));
				
			}
			nbLinesComputed += countByDay.size();
		}
		return ResponseEntity.ok("Compute completed on " + nbLinesComputed + " lines.");
	}

	@GetMapping("/lastComputationAllAccessesAllPage")
	public ResponseEntity<LocalDateTime> lastComputationAllAccessesAllPage(@RequestParam Long customerId) {
		NbHitsPerMonth nbHits = nbHitsPerMonthRepository.findTopByCustomerIdOrderByCalculationDateDesc(customerId);
		if (nbHits != null) {
			return ResponseEntity.ok(nbHits.getCalculationDate());
		}
		return ResponseEntity.notFound().build();
	}
	
	/**
		
	@GetMapping(value="/create", produces="application/json")
	@ResponseBody
	public Supervisor createSupervisor(
			@RequestParam(required = true) String username, @RequestParam(required = true) String password,
			@RequestParam(required = true) String firstName, @RequestParam(required = true) String lastName) {
		Supervisor newUser = new Supervisor(username, password, firstName, lastName, Role.USER);
		Supervisor theCreatedUser = supervisorRepository.save(newUser);
		return theCreatedUser;
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Supervisor> getAllSupervisors() {
		return supervisorRepository.findAll();
	}

	*/
}
