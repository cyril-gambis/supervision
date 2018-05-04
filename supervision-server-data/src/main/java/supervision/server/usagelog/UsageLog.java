package supervision.server.usagelog;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;

import lombok.Data;
import supervision.server.user.User;

@Data
@Entity
@Table(name = "usage_log")
public class UsageLog implements Serializable {
    private static final long serialVersionUID = 1L;
    //private static final Logger log = Logger.getLogger(UsageLog.class.getName());

    @Column(name = "ID", precision = 19)
    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    @Column(name = "`DATE`", nullable = false, length = 19)
    private LocalDateTime date;

    @NotNull
    @Column(name = "TARGET_ID", nullable = false, precision = 19)
    private Long targetId;

    @NotNull
    @JoinColumn(name = "USAGE_LOG_PAGE_FK", nullable = false)
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private UsageLogPage usageLogPage;

    @NotNull
    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
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
    	System.out.println("CATEGORY DESCRIPTION");
    	System.out.println("usageLogPage.getUserAction(): ");
    	System.out.println(usageLogPage.getUserAction());
    	System.out.println("usageLogPage.getUserAction().getActionCategory");
    	System.out.println(usageLogPage.getUserAction().getActionCategory());
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