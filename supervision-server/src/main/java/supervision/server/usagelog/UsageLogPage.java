package supervision.server.usagelog;

import lombok.Data;

@Data
public class UsageLogPage {

	private Long id;
    private String url;
    private String description;
    private UserAction userAction;

}