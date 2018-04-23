package supervision.server.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import supervision.server.mail.MailRepository;

@RepositoryRestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/search/findByUsername")
	public ResponseEntity<User> findByUsername(@RequestParam String username) throws Exception {
		Optional<User> userOpt = userRepository.findByUsername(username);
		return userOpt
				.map(user -> ResponseEntity.ok().body(user))
				.orElseGet(() -> ResponseEntity.notFound().build());				
	}

	
	/**
	 * This provide links to various resources of the Spring rest repositories
	 */
	@Autowired
	private RepositoryEntityLinks repositoryEntityLinks;
	
	@RequestMapping("/search/findByUsernameHal")
	public ResponseEntity<Resource<User>> findByUsernameHal(@RequestParam String username) throws Exception {
		Optional<User> userOpt = userRepository.findByUsername(username);
		if (userOpt.isPresent()) {
			Link linkToMail = repositoryEntityLinks
					.linkToSingleResource(MailRepository.class, userOpt.get().getPrimaryEmail().getId())
					.withRel("primaryEmail");
			Resource<User> resource = new Resource<User>(userOpt.get());
			resource.add(linkToMail);
			return ResponseEntity.ok().body(resource);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/* Another way to add links to resources
	  
		@Bean
		public ResourceProcessor<Resource<Person>> personProcessor() {
			return new ResourceProcessor<Resource<Person>>() {

				@Override
				public Resource<Person> process(Resource<Person> resource) {
	      			resource.add(new Link("http://localhost:8080/people", "added-link"));
	      			return resource;
	     		}
	   		};
		}
	 */
}
