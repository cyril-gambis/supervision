package supervision.server.config;

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

import supervision.server.account.Account;
import supervision.server.account.repository.AccountRepository;

/**
 * Configuration of Spring Security and CORS
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
	private AccountRepository accountRepository;
	
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
	 * @author Cyril Gambis
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#userDetailsService()
	 */
	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Account account = accountRepository.findByUsername(username);
				if(account != null) {
					return new User(account.getUsername(), account.getPassword(), 
							true, true, true, true,
							AuthorityUtils.createAuthorityList(account.getSpringSecurityRole()));
				} else {
					throw new UsernameNotFoundException("Could not find the user '" + username + "'");
				}
			}
	      
		};
	}
/*
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
	/*
	http
	.cors()
	.and()
	.requestMatchers()
	.and()
	.authorizeRequests()
	.antMatchers("/actuator/**", "/api-docs/**", "/connectedUser", "/hello").permitAll()
	.antMatchers("/supervision/**").authenticated()
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		
			.and()
			.authorizeRequests()
			//.antMatchers("/login").permitAll()
			.anyRequest().permitAll() // .authenticated()
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
