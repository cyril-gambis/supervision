package supervision.server.usagelog;

import lombok.Data;

@Data
public class CountByDay {

	private final Integer day;
	private final Integer month;
	private final Integer year;
	private final Long count;

}
