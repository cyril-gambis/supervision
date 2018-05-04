package supervision.server.usagelog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = "usage_log_page")
public class UsageLogPage implements Serializable {
    private static final long serialVersionUID = 1L;
//    private static final Logger log = Logger.getLogger(UsageLogPage.class.getName());

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;

    @NotEmpty
    @Size(max = 75)
    @Column(name = "URL", nullable = false, length = 75)
    private String url;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @JoinColumn(name = "USAGE_LOG_ACTION_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private UserAction userAction;
    
    public String getActionTypeDescription() {
    	if (userAction != null && userAction.getActionType() != null
    			&& userAction.getActionType().getDescription() != null) {
    		return userAction.getActionType().getDescription();
    	} else {
    		return "";
    	}
    }

    public String getCategoryDescription() {
    	System.out.println("CATEGORY DESCRIPTION");
    	System.out.println("userAction: ");
    	System.out.println(userAction);
    	System.out.println("userAction.getActionCategory");
    	System.out.println(userAction.getActionCategory());
    	if (userAction != null && userAction.getActionCategory() != null
    			&& userAction.getActionCategory().getDescription() != null) {
    		return userAction.getActionCategory().getDescription();
    	} else {
    		return "";
    	}
    }

    public String getEntityName() {
    	if (userAction != null && userAction.getActionCategory() != null
    			&& userAction.getActionCategory().getEntityName() != null) {
    		return userAction.getActionCategory().getEntityName();
    	} else {
    		return "";
    	}
    }

    public Long getPageId() {
    	return id;
    }

}