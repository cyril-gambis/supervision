package supervision.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@Configuration
// By default, creates a security filter which authenticates via incoming OAuth2 token
// The filter is an instance of WebSecurityConfigurerAdapter which has hard-coded order of 3
// --> Need to add security.oauth2.resource.filter-order = 3 in application.properties file
//
// Resource server has authority to define permission for any endpoint
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	// Same token service as the authorization server, because same code base,
	// so we are reusing the same bean (not necessary if the two server runs
	// separately)
//	@Autowired
//	private ResourceServerTokenServices tokenServices;

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;
	
	@Autowired
	private DefaultTokenServices tokenServices;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		//resources.resourceId(resourceIds).tokenServices(tokenServices());
		resources.tokenServices(tokenServices);
	}

	
/*	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.requestMatchers()
			.and()
			.authorizeRequests()
			.antMatchers("/actuator/**", "/api-docs/**", "/connectedUser", "/hello").permitAll()
			.antMatchers("/supervision/**").authenticated()
			.antMatchers("/users-custom/**").authenticated();
	}
*/	

}
