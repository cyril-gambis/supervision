package supervision.server.user;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import supervision.server.user.client.UserClient;

@RepositoryRestController
@RequestMapping(path="/users")
public class UserController {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private UserClient userClient;

	@RequestMapping("/search/findByMail")
	public ResponseEntity<User> findByMail(@RequestParam(required = false) String mail) throws Exception {
		
		LOGGER.info("Inside findByMail");

		User userByMail = userClient.findByMail(mail);
		return Optional
				.ofNullable(userByMail)
				.map(user -> ResponseEntity.ok().body(user))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@RequestMapping("/search/findByPrimaryEmailAddress")
	public ResponseEntity<User> findByPrimaryEmailAddress(@RequestParam(required = false) String emailAddress) throws Exception {
		User userByMail = userClient.findByPrimaryEmailAddress(emailAddress);
		return new ResponseEntity<User>(userByMail, HttpStatus.OK);
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String SUPERVISION_DATA_ID = "SUPERVISION-DATA";
	
	@RequestMapping("/userByMail")
	public User userByMail(@RequestParam(required = false) String mail) throws Exception {

		//ParameterizedTypeReference<List<Friend>> reference = new ParameterizedTypeReference<List<Friend>>() { };
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://" + SUPERVISION_DATA_ID + "/api/v1.0/userByMail")
				.queryParam("mail", mail);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<User> retour = restTemplate.exchange(
				builder.toUriString(),
				HttpMethod.GET,
				entity,
				User.class);
		
		return retour.getBody();
	}
	
		
	@PreAuthorize("#oauth2.hasScope('read')") // 	@PreAuthorize("#oauth2.hasScope('write')")
	@GetMapping(value="/{id}")
	@ResponseBody
	public User findById(@PathVariable long id) {
		User user = userClient.findByIdWithMail(id);
		return user;
	}

	@GetMapping("")
	public List<User> findAll() {
		return userClient.findAll();
	}
}
