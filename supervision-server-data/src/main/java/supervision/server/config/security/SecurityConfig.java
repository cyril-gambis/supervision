package supervision.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private DefaultTokenServices tokenServices;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		//resources.resourceId(resourceIds).tokenServices(tokenServices());
		resources.tokenServices(tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http

			// Stateless - no session for JWT mode
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
			.and()
			.authorizeRequests()
			.anyRequest().permitAll()
	;
//			.and()
			// form login configuration isn't necessary for Password flow
//			.formLogin().permitAll();

/*			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
			.and()
			.cors()
			
			.and()
			.httpBasic().realmName(securityRealm)
			
			.and()
			.csrf().disable();			
*/	}
}
