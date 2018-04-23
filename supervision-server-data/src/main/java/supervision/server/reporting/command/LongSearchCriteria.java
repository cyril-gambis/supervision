package supervision.server.reporting.command;

import lombok.Data;
import lombok.NonNull;

/**
 * {@code @Data} annotation creates getter and setter.
 * A constructor with all fields set as {@code @NonNull} will also be created
 * 
 * @author cyrilg
 *
 */
@Data
public class LongSearchCriteria implements SearchCriteria {
	
	@NonNull
	private String key;
	@NonNull
	private ReportOperator operator;
	@NonNull
	private Long value;
	
}
