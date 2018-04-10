package supervision.server.account.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import supervision.server.account.Account;
import supervision.server.account.repository.AccountRepository;
import supervision.server.user.User;

@Controller
public class AccountCustomController {

	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * This method is based on the Spring Security context
	 * 
	 * @date 09/04/2018
	 * @author Cyril Gambis
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping(value="/currentAccount")
	@ResponseBody
	public Account currentAccount() {
		
		Account account = null;
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			String username = authentication.getName();
			if (username != null) {
				account = accountRepository.findByUsername(username);
			}
		}

		return account;
	}

	@GetMapping(value="/currentUser")
	@ResponseBody
	public User currentUser() {
		
		Account account = null;
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			String username = authentication.getName();
			if (username != null) {
				account = accountRepository.findByUsername(username);
			}
		}

		return (account == null) ? null : account.getUser();
	}
	
	@Autowired
	private TokenStore tokenStore;
	
	private static final String TOKEN_PREFIX = "Bearer ";
	
	/**
	 * This method is based on the token store and the security header in the request
	 * 
	 * @date 09/04/2018
	 * @author Cyril Gambis
	 * @param header
	 * @return
	 */
	@GetMapping(value="/currentUsername2")
	@ResponseBody
	public String currentUsername2(@RequestHeader("Authorization") String header) {
		
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return "Authorization header invalid";
        } else {
			OAuth2Authentication oaa = tokenStore.readAuthentication(header.replace(TOKEN_PREFIX, ""));
			String username = ((UserDetails) oaa.getPrincipal()).getUsername();
			return (username == null)? "<Not connected>" : username;
        }
	}
	
	/**
	 * This method is based on automatic injection of Principal by Spring in
	 * methods of a Controller
	 * (Authentication is injected in the same way, and authentication.getName() can
	 * also be used) 
	 * 
	 * @date 09/04/2018
	 * @author Cyril Gambis
	 * @param principal
	 * @return
	 */
	@GetMapping(value = "/currentUsername3")
	@ResponseBody
	public String currentUsername3(Principal principal) {
		return (principal != null) ? principal.getName() : "<Not connected>";
	}

	/**
	 * Getting connected user directly from request
	 * 
	 * @date 09/04/2018
	 * @author Cyril Gambis
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/currentUsername4")
	@ResponseBody
	public String currentUsername4(HttpServletRequest request) {
		return (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "<Not connected>";
	}

}
