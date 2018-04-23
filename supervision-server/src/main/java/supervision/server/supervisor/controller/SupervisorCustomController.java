package supervision.server.supervisor.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import supervision.server.supervisor.Role;
import supervision.server.supervisor.Supervisor;
import supervision.server.supervisor.repository.SupervisorRepository;

/**
 * IMPORTANT: if you use @RepositoryRestController, you must put a @RequestMapping,
 * otherwise your controllers will not be created and accessing them will result in Error 404
 * 
 * @date 16/04/2018
 * @author Cyril Gambis
 */
@RepositoryRestController
@RequestMapping(path="/supervisors-custom")
public class SupervisorCustomController {

	@Autowired
	private SupervisorRepository supervisorRepository;
	
	/**
	 * This method is based on the Spring Security context
	 * 
	 * @date 09/04/2018
	 * @author Cyril Gambis
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping(value="/currentSupervisor")
	@ResponseBody
	public Supervisor currentSupervisor() {
		
		Supervisor supervisor = null;
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			String username = authentication.getName();
			if (username != null) {
				supervisor = supervisorRepository.findByUsername(username);
			}
		}

		return supervisor;
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
	@GetMapping(value="/currentSupervisorUsername")
	@ResponseBody
	public String currentSupervisorUsername(@RequestHeader("Authorization") String header) {
		
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
	@GetMapping(value = "/currentSupervisorUsername2")
	@ResponseBody
	public String currentSupervisorUsername2(Principal principal) {
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
	@GetMapping(value = "/currentSupervisorUsername3")
	@ResponseBody
	public String currentSupervisorUsername3(HttpServletRequest request) {
		return (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "<Not connected>";
	}

	
	
	/**
	 * @RequestParam is optional, default to required = true
	 * 
	 * @date 23/03/2018
	 * @author Cyril Gambis
	 * @param name
	 * @param lastName
	 * @return
	 */
	@GetMapping(value="/create", produces="application/json")
	@ResponseBody
	public Supervisor createSupervisor(
			@RequestParam(required = true) String username, @RequestParam(required = true) String password,
			@RequestParam(required = true) String firstName, @RequestParam(required = true) String lastName) {
		Supervisor newUser = new Supervisor(username, password, firstName, lastName, Role.USER);
		Supervisor theCreatedUser = supervisorRepository.save(newUser);
		return theCreatedUser;
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Supervisor> getAllSupervisors() {
		return supervisorRepository.findAll();
	}

	
}
