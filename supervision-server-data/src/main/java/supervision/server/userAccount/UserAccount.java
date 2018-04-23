package supervision.server.userAccount;

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

import lombok.Data;
import supervision.server.customer.Customer;
import supervision.server.mail.Mail;
import supervision.server.user.User;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
//    private static final Logger log = Logger.getLogger(UserAccount.class.getName());

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;

    @NotNull
    @Column(name = "DELETED", nullable = false, precision = 3)
    private Integer deleted;

    @NotNull
    @Column(name = "ACCESS_DENIAL_REASON", nullable = false, precision = 10)
    private Integer accessDenialReason;

    @NotNull
    @Column(name = "ACCESS_STATUS", nullable = false, precision = 10)
    private Integer accessStatus;

    @Size(max = 256)
    @Column(name = "DEPARTMENT", length = 256)
    private String department;

    @Size(max = 256)
    @Column(name = "NAME", length = 256)
    private String name;

    @Column(name = "STATUS", precision = 10)
    private Integer status;

    @Size(max = 256)
    @Column(name = "`POSITION`", length = 256)
    private String position;

    @JoinColumn(name = "USER_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private User user;

    @JoinColumn(name = "EMAIL_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private Mail email;

    @JoinColumn(name = "CUSTOMER_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private Customer customer;

}