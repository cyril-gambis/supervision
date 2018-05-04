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
    private UserAction userAction;

    public Long getPageId() {
    	return id;
    }

}