package supervision.server.usagelog;

import lombok.Data;
import supervision.server.user.User;

@Data
public class CountByUser {
	
	private final User user;
	private final Long count;

}
