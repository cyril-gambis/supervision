package supervision.server.usagelog;

import lombok.Data;
import supervision.server.user.User;

@Data
public class UsageLogCountByUser {
	
	private final User user;
	private final Long count;

}
