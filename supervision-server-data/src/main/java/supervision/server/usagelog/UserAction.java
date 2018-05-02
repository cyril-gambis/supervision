package supervision.server.usagelog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;

import lombok.Data;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = "USAGE_LOG_ACTION")
public class UserAction implements Serializable {
    private static final long serialVersionUID = 1L;
//    private static final Logger log = Logger.getLogger(UserAction.class.getName());

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;

    @JoinColumn(name = "USAGE_LOG_ACTION_CATEGORY_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private ActionCategory actionCategory;

    @JoinColumn(name = "USAGE_LOG_ACTION_TYPE_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private ActionType actionType;

    public String entityClassName() {
        return UsageLogPage.class.getSimpleName();
    }

}