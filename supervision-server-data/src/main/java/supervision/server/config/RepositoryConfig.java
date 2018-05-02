package supervision.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import supervision.server.customer.Customer;
import supervision.server.mail.Mail;
import supervision.server.user.User;
import supervision.server.userAccount.UserAccount;

/**
 * With this config, the exported JSON will include the id of the object.
 * 
 * Note: this is not considered as the way to do HATEOAS
 * The link "self" must be used to update/get the data of the resource
 * 
 * @author cyrilg
 *
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
	
    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(UserAccount.class);
        config.exposeIdsFor(Mail.class);
        config.exposeIdsFor(Customer.class);

        //config.setDefaultMediaType(MediaType.APPLICATION_JSON);
    }

}