package supervision.server.usagelog;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import supervision.server.user.User;

@Data
public class UsageLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDateTime date;
    private Long targetId;
    private UsageLogPage usageLogPage;
    private User user;

    public String getActionTypeDescription() {
    	if (usageLogPage != null && usageLogPage.getUserAction() != null && usageLogPage.getUserAction().getActionType() != null
    			&& usageLogPage.getUserAction().getActionType().getDescription() != null) {
    		return usageLogPage.getUserAction().getActionType().getDescription();
    	} else {
    		return "";
    	}
    }

    public String getCategoryDescription() {
    	if (usageLogPage != null && usageLogPage.getUserAction() != null && usageLogPage.getUserAction().getActionCategory() != null
    			&& usageLogPage.getUserAction().getActionCategory().getDescription() != null) {
    		return usageLogPage.getUserAction().getActionCategory().getDescription();
    	} else {
    		return "";
    	}
    }

    public String getEntityName() {
    	if (usageLogPage != null && usageLogPage.getUserAction() != null && usageLogPage.getUserAction().getActionCategory() != null
    			&& usageLogPage.getUserAction().getActionCategory().getEntityName() != null) {
    		return usageLogPage.getUserAction().getActionCategory().getEntityName();
    	} else {
    		return "";
    	}
    }
}