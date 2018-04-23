package supervision.server.reporting.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import supervision.server.reporting.command.SearchCriteria;


public abstract class SpecificationBuilder<T> {

	private final List<SearchCriteria> params;
	
	List<Long> planzoneAuthorizedIds = Arrays.asList(new Long(1), new Long(2), new Long(3), new Long(63192633), new Long(63153412), new Long(63194918));
	
	private static final boolean RESTRICT_PLANZONES_REPORT = true;
	
	public SpecificationBuilder() {
		this.params = new ArrayList<SearchCriteria>();
	}
	
	public SpecificationBuilder<T> with(SearchCriteria searchCriteria) {
		params.add(searchCriteria);
		return this;
	}

	public Specification<T> build() {

		// Restrict specifically to the table (like filter the deleted tasks, for the task table)
		// Must not be null, it will be the first predicate on which the query will be build
		Specification<T> result = getSpecificRestrictionPredicate();

		if (result == null) {
			throw new RuntimeException("The specific restrcition predicate must not be null because the whole query is based on it.");
		}
		
		if (params.size() > 0) {
			List<Specification<T>> specs = new ArrayList<Specification<T>>();
			for (SearchCriteria param : params) {
				specs.add(createSpecification(param));
			}
		
			for (int i = 0; i < specs.size() ; i++) {
				result = Specifications.where(result).and(specs.get(i));
			}
		}
		
		// Restrict the result to the planzone of the user / the authorized planzones
		if (RESTRICT_PLANZONES_REPORT) {
			result = Specifications.where(result).and(getPlanzoneRestrictionPredicate(planzoneAuthorizedIds));
		}

		return result;
	}

	
	protected abstract Specification<T> getSpecificRestrictionPredicate();

	protected abstract Specification<T> getPlanzoneRestrictionPredicate(final List<Long> planzoneAuthorizedIds);

	protected abstract Specification<T> createSpecification(SearchCriteria criteria);
}