package supervision.server.customer;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;

import lombok.Data;
import supervision.server.license.License;
import supervision.server.userAccount.UserAccount;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
//    private static final Logger log = Logger.getLogger(Planzone.class.getName());

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;

    @Size(max = 256)
    @Column(name = "NAME", length = 256)
    private String name;

    @Column(name = "SIGNUP_DATE", length = 19)
    private LocalDateTime signupDate;

    @Size(max = 256)
    @Column(name = "SITE", length = 256)
    private String site;

    @Column(name = "STATUS", precision = 10)
    private Integer status;

    @Size(max = 4000)
    @Column(name = "BACK_OFFICE_COMMENTS", length = 4000)
    private String backOfficeComments;

    @Column(name = "DELETED", precision = 3)
    private Integer deleted;

    @JoinColumn(name = "CREATED_BY_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private UserAccount createdBy;

    @NotNull
    @JoinColumn(name = "LICENSE_FK", nullable = false, unique = true)
    @OneToOne
    private License license;

    // One to many
//    @JsonBackReference
//    @OneToMany(mappedBy = "planzone")
//    private List<Project> projects = new ArrayList<Project>();

}