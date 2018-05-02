package supervision.server.mail;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import supervision.server.user.User;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "email")
@Data
public class Mail implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;
    
    @Size(max = 4000)
    @Column(name = "DELIVERY_ERROR", length = 4000)
    private String deliveryError;
    
    @Column(name = "DELIVERY_STATUS", precision = 10)
    private Integer deliveryStatus;

    @Size(max = 180)
    @Column(name = "EMAIL_ADDRESS", length = 180)
    private String emailAddress;
    
    @NotNull
    @Column(name = "IS_TRUSTED", nullable = false, precision = 3)
    private Integer isTrusted;

    @Column(name = "LAST_ERROR_DATE", length = 19)
    private LocalDateTime lastErrorDate;
    
    @Column(name = "LAST_MAILING_DATE", length = 19)
    private LocalDateTime lastMailingDate;

    @Column(name = "MAIL_COUNT_AFTER_ERROR", precision = 10)
    private Integer mailCountAfterError;
    
    @Column(name = "NEXT_STATUS_RESET_DATE", length = 19)
    private LocalDateTime nextStatusResetDate;

    @NotNull
    @Column(name = "NO_SPAM", nullable = false, precision = 3)
    private Integer noSpam;

    // Many to one
    @JoinColumn(name = "USER_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    @JsonBackReference
    private User user;

    public String entityClassName() {
        return Mail.class.getSimpleName();
    }

    /**
     * Apply the default values.
     */
    public Mail withDefaults() {
        setIsTrusted(0);
        setMailCountAfterError(0);
        setNoSpam(0);
        return this;
    }

}