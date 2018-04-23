package supervision.server.reporting.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import supervision.server.reporting.command.SearchCriteria;
import supervision.server.usagelog.UsageLog;


/**
 * Builds a list of SearchCriterias for UserActions
 * 
 * @date 31/07/2017
 * @author Cyril Gambis
 */
public class UsageLogSpecificationBuilder extends SpecificationBuilder<UsageLog> {	
	
	/**
	 *
	 * 
	 * @date 03/08/2017
	 * @author Cyril Gambis
	 * @see com.augeo.api.report.specification.SpecificationBuilder#getPlanzoneRestrictionPredicate()
	 */
	@Override
	public Specification<UsageLog> getPlanzoneRestrictionPredicate(final List<Long> planzoneAuthorizedIds) {
		Specification<UsageLog> planzoneRestrictionPredicate = new Specification<UsageLog>() {

			@Override
			public Predicate toPredicate(Root<UsageLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Path<Long> path = root.join(UsageLog_._.planzone).get(Planzone_.id);
	//			return path.in(planzoneAuthorizedIds);
				return cb.and(); // always true
			}

		};
		return planzoneRestrictionPredicate;
	}

	@Override
	protected Specification<UsageLog> createSpecification(SearchCriteria criteria) {
		return new UsageLogSpecification(criteria);
	}
	
	@Override
	protected Specification<UsageLog> getSpecificRestrictionPredicate() {
		return new Specification<UsageLog>() {
			@Override
			public Predicate toPredicate(Root<UsageLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and();
			}			
		};
	}
}
