package supervision.server.usagelog;

import java.time.LocalDateTime;

import lombok.Data;
import supervision.server.user.User;

@Data
public class UsageLogFull {
	private final Long id;
	private final Long pageId;
	private final LocalDateTime date;
	private final Long targetId;
	private final String url;
	private final String page;
	private final String category;
	private final String entity;
	private final String actionType;
	private final String firstName;
	private final String lastName;
	private final String emailAddress;
	private final User user;
}
