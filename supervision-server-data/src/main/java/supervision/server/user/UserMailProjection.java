package supervision.server.user;

import org.springframework.data.rest.core.config.Projection;

import supervision.server.mail.Mail;

@Projection(name= "usermail", types = User.class)
public interface UserMailProjection {

	Long getId();

	String getCompany();
    
    String getCompanySite();
    
    String getCountry();
    
    Integer getDeleted();
    
    String getFirstName();
    
    String getLastName();
    
    Integer getLoginFailureCount();

    String getPhone();
    
    Integer getRequireVerifiedEmail();

    String getTitle();
    
    Integer getCompanySize();

    Mail getPrimaryEmail();
}
