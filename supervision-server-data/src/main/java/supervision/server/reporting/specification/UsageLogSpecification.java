package supervision.server.reporting.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import supervision.server.reporting.command.SearchCriteria;
import supervision.server.usagelog.UsageLog;


/**
 * UsageLogSpecification will help create queries on the usage logs 
 * 
 * This code has been written with : http://www.baeldung.com/rest-api-search-language-spring-data-specifications
 * 
 * http://localhost:XXXX/api/searchUsageLogs?search=id>2
 * http://localhost:XXXX/api/searchUsageLogs?search=taskId<40,planzoneName:ugeo
 * http://localhost:XXXX/api/searchUsageLogs?search=startDate<2011-03-01
 * http://localhost:XXXX/api/searchUsageLogs?search=createdByName:Bern,startDate:2017-02-09
 * 
 * {@code @Data} annotation creates getter and setter.
 * A constructor with all fields set as {@code @NonNull} will also be created
 * 
 * @date 31/07/2017
 * @author Cyril Gambis
 */
public class UsageLogSpecification extends ReportSpecification<UsageLog> {

    private static final String CRITERIA_DATE_TIME = "dateTime";

    private static final String CRITERIA_USER_NAME = "userName";

    private static final String CRITERIA_DESCRIPTION = "description";
	
	private static final String CRITERIA_CATEGORY = "category";
	
	private static final String CRITERIA_ENTITY_NAME = "entityName";
	
	private static final String CRITERIA_TYPE = "type";
	
	public UsageLogSpecification(SearchCriteria searchCriteria) {
		super(searchCriteria);
	}
	
	@Override
	public Predicate toPredicate(Root<UsageLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
		Predicate predicate = buildJoinPredicate(root, criteria.getKey(), builder);

		if (predicate == null) {
			//Class<? extends Object> criteriaJavaType = root.get(criteria.getKey()).getJavaType();
			
			predicate = toPredicate(root, builder);
		}
		
		return predicate;
	}


	private Predicate buildJoinPredicate(Root<UsageLog> root, String key, CriteriaBuilder builder) {
		// Get the path to the criteria, for instance: "task.project.name" to check against the name
		Path<String> path = getPathFromKey(root, key);
		if (path == null) {
			return null;
		} else {
			return builder.like(path, "%" + criteria.getValue().toString() + "%");
		}
	}

	/**
	 * Get the path to the criteria, for instance: "task.project.name" to check against the name
	 * 
	 * It uses the key to find the correct path
	 * 
	 * @date 01/08/2017
	 * @author Cyril Gambis
	 * @param root
	 * @param key
	 * @return
	 */
	private Path<String> getPathFromKey(Root<UsageLog> root, String key) {
		switch (key == null ? "" : key) {
//		case CRITERIA_USER_NAME:
//			return root.join(UsageLog_.userIdTask_.project).get(Project_.name);
//		case CRITERIA_PLANZONE_NAME:
//			return root.join(Task_.planzone).get(Planzone_.name);
//		case CRITERIA_CREATED_BY_NAME:
//			return root.join(Task_.createdBy).get(UserAccount_.name);
//		case CRITERIA_ALLOCATED_RESOURCE_NAME:
//			return root.join(Task_.allocations).join(Allocation_.resource).get(Resource_.name);
		default:
			return null;
		}
	}
	

}
