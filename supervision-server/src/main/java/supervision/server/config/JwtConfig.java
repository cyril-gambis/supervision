package supervision.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * JWT will manage the tokens: creation, decoding
 * 
 * @date 30/03/2018
 * @author Cyril Gambis
 */
@Configuration
public class JwtConfig {

	@Value("${security.signing-key}")
	private String signingKey;

	@Autowired
	private DataSource dataSource;

	/**
	 * Used to decode access token 
	 * 
	 * @author Cyril Gambis
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}
	
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
	
	/**
	 * Used by authorization server to enable resource server to decode access tokens 
	 * 
	 * @author Cyril Gambis
	 * @return
	 *
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	
	@Bean
	// This is primary to avoid any potential duplication with another token service of the same name
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
*/
}
