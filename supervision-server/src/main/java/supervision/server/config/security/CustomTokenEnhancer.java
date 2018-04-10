package supervision.server.config.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * We add the tenant information to the token
 * 
 * @author Cyril Gambis
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		// Here we put the tenant for a multitenancy application
		additionalInfo.put("tenant", 123456);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}

}
