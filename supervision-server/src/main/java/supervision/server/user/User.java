package supervision.server.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import supervision.server.mail.Mail;

//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "ID", precision = 19)
    @Id
    private Long id;

    @Size(max = 256)
    @Column(name = "COMPANY", length = 256)
    private String company;
    
    @Size(max = 256)
    @Column(name = "COMPANY_SITE", length = 256)
    private String companySite;
    
    @Size(max = 256)
    @Column(name = "COUNTRY", length = 256)
    private String country;
    
    @NotNull
    @Column(name = "DELETED", nullable = false, precision = 3)
    private Integer deleted;
    
    @Size(max = 256)
    @Column(name = "FIRST_NAME", length = 256)
    private String firstName;
    
    @Size(max = 256)
    @Column(name = "LAST_NAME", length = 256)    private String lastName;
    private Integer loginFailureCount;
    
    @Size(max = 256)
    @Column(name = "`PASSWORD`", length = 256)
    private String password;

    @Size(max = 256)
    @Column(name = "PHONE", length = 256)
    private String phone;
    
    @NotNull
    @Column(name = "REQUIRE_VERIFIED_EMAIL", nullable = false, precision = 3)
    private Integer requireVerifiedEmail;

    @Size(max = 256)
    @Column(name = "TITLE", length = 256)
    private String title;
    
    @Column(name = "COMPANY_SIZE", precision = 3)
    private Integer companySize;

    // Many to one
/*    @JoinColumn(name = "SMALL_PICTURE_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private Picture smallPicture;
*/
//    private Picture largePicture;
    
    /*
     *     @JoinColumn(name = "PREFERENCES_FK")
    @ManyToOne
    @NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)

     */
//    private UserPreferences preferences;

    // One to one
    @NotNull
    @JoinColumn(name = "PRIMARY_EMAIL_FK", nullable = false, unique = true)
    @OneToOne
    @JsonManagedReference
    private Mail primaryEmail;

    // Many to many
    /*
     * @JoinTable(name = "account_manager", joinColumns = @JoinColumn(name = "USER_FK") , inverseJoinColumns = @JoinColumn(name = "ACCOUNT_FK") )
    @ManyToMany
     */
//    private List<Account> accounts = new ArrayList<Account>();

    public String entityClassName() {
        return User.class.getSimpleName();
    }
}