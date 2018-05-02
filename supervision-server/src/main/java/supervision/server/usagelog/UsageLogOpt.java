package supervision.server.usagelog;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsageLogOpt {

	private Long id;
	private Long customerId;
	private Long userId;
	private Long pageId;
	private LocalDateTime date;
	
}
