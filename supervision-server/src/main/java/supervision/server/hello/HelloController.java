package supervision.server.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import supervision.server.user.User;
import supervision.server.user.repository.UserRepository;

@Controller
public class HelloController {

	@GetMapping(value="/hello")
	@ResponseBody
	public String index(@RequestParam(required = false) String name) {
		if (name != null) {
			return "Hello " + name;
		} else {
			return "Hello Guest";
		}
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping(value="/user/{id}")
	@ResponseBody
	public User findById(@PathVariable long id) {
		return userRepository.findOne(id);
	}

	@PreAuthorize("#oauth2.hasScope('write')")
	@GetMapping(value="/user2/{id}")
	@ResponseBody
	public User findById2(@PathVariable long id) {
		return userRepository.findOne(id);
	}
	
	@Autowired
	private TokenStore tokenStore;
	
	private static final String TOKEN_PREFIX = "Bearer ";
	
	@GetMapping(value="/connectedUser")
	@ResponseBody
	public String connectedUser(@RequestHeader("Authorization") String header) {
		
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return "Authorization header invalid";
        } else {
			OAuth2Authentication oaa = tokenStore.readAuthentication(header.replace(TOKEN_PREFIX, ""));
			String username = ((UserDetails) oaa.getPrincipal()).getUsername();
			return (username == null)? "<Not connected>" : username;
        }
	}
}
