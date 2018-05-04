package supervision.server.usagelog;

import lombok.Data;
import supervision.server.user.User;

@Data
public class CountByUserByMonth {
	
	private final User user;
	private final Long count;
	private final Integer month;
	private final Integer year;

}
