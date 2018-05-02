package supervision.server.user;

import java.io.Serializable;

import lombok.Data;
import supervision.server.mail.Mail;

//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String company;
    private String companySite;
    private String country;
    private Integer deleted;
    private String firstName;
    private String lastName;
    private Integer loginFailureCount;
    private String password;
    private String phone;
    private Integer requireVerifiedEmail;
    private String title;
    private Integer companySize;

    // Many to one
/*
    private Picture smallPicture;
*/
//    private Picture largePicture;
    
//    private UserPreferences preferences;

    // One to one
    private Mail primaryEmail;

    // Many to many
//    private List<Account> accounts = new ArrayList<Account>();

}