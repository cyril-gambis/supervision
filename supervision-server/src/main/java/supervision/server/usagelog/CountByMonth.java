package supervision.server.usagelog;

import lombok.Data;

@Data
public class CountByMonth {
	
	private final Long count;
	private final Integer month;
	private final Integer year;

}
