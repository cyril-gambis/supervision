package supervision.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * The Authorization Server manage the access for both client application and end user
 * It has symetric key signing with resource server here (but could also be public/private keys,
 * like explained here: http://www.baeldung.com/spring-security-oauth-jwt)
 * 
 * With this AuthorizationServerConfigurer (impemented by AuthorizationServerConfigurerAdapter),
 * Spring boot will swith off auto-configuration and use the custom config
 * 
 * Based on https://medium.com/@nydiarra/secure-a-spring-boot-rest-api-with-json-web-token-reference-to-angular-integration-e57a25806c50
 * 
 * @author Cyril Gambis
 */
@Configuration
// Convenience annotation for enabling an Authorization Server (i.e. an AuthorizationEndpoint and a TokenEndpoint)
// in the current application context
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// The id of the client application (client needs this to
	// be allowed to send requests to the server)
	@Value("${security.jwt.client-id}")
	private String clientId;
	
	// The client's application password
	// If several clients, each one will need to subscribe to a separate API that will
	// manage the list of authorized clients, and it will be store in a database
	@Value("${security.jwt.client-secret}")
	private String clientSecret;
	
	// We define grant type "password" because it is not enabld by default
	@Value("${security.jwt.grant-type}")
	private String grantType;
	
	// Level of access we are allowing to resources
	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite;

	// The resource id must be also on the resource server as well
	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	/**
	 * Spring's authentication manager takes care checking user credential validity
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}


	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.jdbc(dataSource)
			.withClient(clientId)
			.authorizedGrantTypes("implicit")
			.scopes(scopeRead)
			.autoApprove(true)
			.and()
			.withClient("clientIdPassword")
			.secret(clientSecret)
			.authorizedGrantTypes("password", "authorization_code", "refresh_token")
			.scopes(scopeRead, scopeWrite);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		// The token enhancer will chain multiple types of claims containing different
		// information
//		No need to add claims, so enhancer is disabled
//		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		
		endpoints
			.tokenStore(tokenStore)
//			.accessTokenConverter(accessTokenConverter)
//			No need to add claims, so enhancer is disabled
//			.tokenEnhancer(enhancerChain)
			
			// the authenticationManager is used for the "password" grant type
			.authenticationManager(authenticationManager);
	}

	
	
	@Value("classpath:schema.sql")
	private Resource schemaScript;

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
	    DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}
	 
	private DatabasePopulator databasePopulator() {
	    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(schemaScript);
	    return populator;
	}
	
}
