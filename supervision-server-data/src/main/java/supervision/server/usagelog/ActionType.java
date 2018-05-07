package supervision.server.usagelog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

//Enable Caching of this entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@Data
@Entity
@Table(name = "USAGE_LOG_ACTION_TYPE")
public class ActionType implements Serializable {
    private static final long serialVersionUID = 1L;
//    private static final Logger log = Logger.getLogger(ActionType.class.getName());

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;
    @Size(max = 75)
    @Column(name = "DESCRIPTION", length = 75)
    private String description;

    public String entityClassName() {
        return ActionType.class.getSimpleName();
    }
}