package supervision.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import supervision.server.supervisor.Supervisor;
import supervision.server.supervisor.repository.SupervisorRepository;

/**
 * Supervision is based on OAuth: an authorization server and a resource server,
 * except the same application has both roles
 * 
 * In this class, we have the common parts of the security config, the more specific
 * parts are in AuthorizationServerConfig.java and ResourceServerConfig.java,
 * and the JWT configuration (token store...) is in JwtConfig
 * 
 * @author Cyril Gambis
 */
@Configuration
// Enable Spring Security and hints Spring boot to apply all sensitive defaults
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;
	
	/*
	 * The userDetailsService is defining inside this SecurityConfig currently
	@Autowired
	private UserDetailsService userDetailsService;
	*/
	
	@Autowired
	private SupervisorRepository supervisorRepository;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
			//.passwordEncoder(new ShaPasswordEncoder(encodingStrength));
	}
	
	/**
	 * Retrieve accounts in database
	 * 
	 * The UserDetails is the "Principal" that is store in the spring security context upon
	 * authorization/authentication
	 * 
	 * @author Cyril Gambis
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#userDetailsService()
	 */
	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Supervisor supervisor = supervisorRepository.findByUsername(username);
				if(supervisor != null) {
					return new User(supervisor.getUsername(), supervisor.getPassword(), 
							true, true, true, true,
							AuthorityUtils.createAuthorityList(supervisor.getSpringSecurityRole()));
				} else {
					throw new UsernameNotFoundException("Could not find the user '" + username + "'");
				}
			}
	      
		};
	}
/*
 * This CORS config is not compatible with Spring OAuth (because of the orders
 * of Spring's Security filters).
 * 
 * The Cors Config is done in CorsConfig.java
 * 
 *
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		//config.addAllowedOrigin("http://domain1.com");
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
*/	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

			// Stateless - no session for JWT mode
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
			.and()
			.authorizeRequests()
			.antMatchers("/oauth/**").permitAll()
			.anyRequest().authenticated()
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
