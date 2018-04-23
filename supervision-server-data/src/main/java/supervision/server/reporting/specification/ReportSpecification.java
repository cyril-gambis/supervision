package supervision.server.reporting.specification;

import java.time.LocalDateTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;
import lombok.NonNull;
import supervision.server.reporting.command.DateSearchCriteria;
import supervision.server.reporting.command.DateTimeSearchCriteria;
import supervision.server.reporting.command.IntegerSearchCriteria;
import supervision.server.reporting.command.ReportOperator;
import supervision.server.reporting.command.SearchCriteria;
import supervision.server.reporting.command.StringSearchCriteria;

@Data
public abstract class ReportSpecification<T> implements Specification<T> {

	@NonNull
	protected SearchCriteria criteria;
	
	protected Predicate toPredicate(Root<T> root, CriteriaBuilder builder) {
		
		ReportOperator operation = criteria.getOperator();
		
		if (criteria instanceof DateSearchCriteria) {
			Expression<LocalDateTime> key = root.<LocalDateTime>get(criteria.getKey());
			LocalDateTime value = ((DateSearchCriteria) criteria).getValue();

			if (value == null) {
				return null;
			}
			
			return toPredicate(key, operation, value, builder);
		} else if (criteria instanceof StringSearchCriteria) {
			Expression<String> key = root.<String>get(criteria.getKey());
			String value = ((StringSearchCriteria) criteria).getValue();
			
			if (value == null) {
				return null;
			}
			
			if (ReportOperator.LIKE.equals(operation)) {
				return builder.like(key, "%" + value + "%");
			} else {
				return toPredicate(key, operation, value, builder);
			}
			
		} else if (criteria instanceof DateTimeSearchCriteria) {
			Expression<LocalDateTime> key = root.<LocalDateTime>get(criteria.getKey());
			LocalDateTime value = ((DateTimeSearchCriteria) criteria).getValue();
	
			if (value == null) {
				return null;
			}
			
			return toPredicate(key, operation, value, builder);
		} else if (criteria instanceof IntegerSearchCriteria) {
			Expression<Integer> key = root.<Integer>get(criteria.getKey());
			Integer value = ((IntegerSearchCriteria) criteria).getValue();
			
			if (value == null) {
				return null;
			}
			
			return toPredicate(key, operation, value, builder);
		} else {
			return null;
		}

	}
	
	private <Y extends Comparable<? super Y>> Predicate toPredicate(Expression<? extends Y> key, ReportOperator operator, Y value, CriteriaBuilder builder) {
		switch (operator) {
		case GT:
			return builder.greaterThan(key, value);
		case LT:
			return builder.lessThan(key, value);
		case EQ:
			return builder.equal(key, value);
		default:
			return null;
		}		
	}
	
//	public Predicate toPredicateFromLocalDateTime(Root<UsageLog> root, CriteriaBuilder builder) {
//
//		Expression<LocalDateTime> key = root.<LocalDateTime>get(criteria.getKey());
//		String operation = criteria.getOperation();
//		LocalDateTime value = null;
//		LocalDate localDateValue = criteria.getLocalDateValue();
//
//		if (localDateValue != null) {
//			value = localDateValue.atStartOfDay();
//		}
//

	
}
