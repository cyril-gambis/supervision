package supervision.server.config.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign client manage calls to other microservices
 * 
 * @date 17/04/2018
 * @author Cyril Gambis
 */
@Configuration
public class FeignClientConfig {

	/**
	 *  The error decoder will transform the generic FeignException to
	 *  a custom exception that we will manage in FeignGlobalControllerAdvice
	 * 
	 * @author Cyril Gambis
	 */
	@Bean
	public FeignErrorDecoder errorDecoder() {
	  return new FeignErrorDecoder();
	}

	/**
	 * Configure Feign logger 
	 * 
	 * @date 18/04/2018
	 * @author Cyril Gambis
	 */
	@Bean
	public Logger.Level feignLogger() {
		return Logger.Level.BASIC;
	}
	
	/**
	 * Create simple RequestInterceptor to forward authentication token
	 * 
	 * @date 18/04/2018
	 * @author Cyril Gambis
	 */
	@Bean
	public RequestInterceptor requestTokenBearerInterceptor() {

	    return new RequestInterceptor() {
	        @Override
	        public void apply(RequestTemplate requestTemplate) {
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
	            requestTemplate.header("Authorization", "Bearer " + details.getTokenValue());                   
	        }

	    };
	}
	
	
	/*
	@Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext,
                                                            OAuth2ProtectedResourceDetails resource) {
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource);
    }
	*/
	/*
	// See https://github.com/spring-cloud/spring-cloud-netflix/issues/675
	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext){
	    return new OAuth2FeignRequestInterceptor(oauth2ClientContext, resource());
	}
	@Value("${security.oauth2.client.userAuthorizationUri}")
	private String authorizeUrl;
	
	@Value("${security.oauth2.client.accessTokenUri}")
	private String tokenUrl;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Bean
	protected OAuth2ProtectedResourceDetails resource() {
	    AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
	    resource.setAccessTokenUri(tokenUrl);
	    resource.setUserAuthorizationUri(authorizeUrl);
	    resource.setClientId(clientId);
	    // TODO: Remove this harcode 
	    resource.setClientSecret("secret");
	    return resource;
	}   
	*/
}
