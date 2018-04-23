package supervision.server.reporting.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
public class StringSearchCriteria implements SearchCriteria{
	
	@NonNull
	private String key;
	@NonNull
	private ReportOperator operator;
	@NonNull
	private String value;

	/**
	 * 
	 * 
	 * @date 01/08/2017
	 * @author Cyril Gambis
	 * @return
	 */
	public LocalDate getLocalDateValue() {
		LocalDate result = null;
		if (value != null && value instanceof String) {
			try {
				result = LocalDate.parse(value);
			} catch (DateTimeParseException e) {
				// Do nothing, result is null
			}
		}
		return result;
	}
	
}
