package supervision.server.config;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import supervision.server.user.User;
import supervision.server.user.UserRepository;

@RestController
public class EurekaClientController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String SUPERVISION_DATA_ID = "SUPERVISION-DATA";
	
	@GetMapping(value="/infosDiscoveryClient")
	String infos() throws Exception {
		final StringBuilder sb = new StringBuilder();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy - HH:mm:ss", Locale.FRENCH);
		
		discoveryClient.getInstances(SUPERVISION_DATA_ID)
			.forEach(serviceInstance -> {
				sb.append(serviceInstance.getServiceId());
				sb.append(" uri=");
				sb.append(serviceInstance.getUri());
			});
		return "<h2>Infos (à " + LocalDateTime.now().format(formatter) + ") : "
				+ sb.toString() + "</h2>";
	}
	
	@RequestMapping("/infos")
	String restInfos() throws Exception {
		
		List<ServiceInstance> instances = discoveryClient.getInstances(SUPERVISION_DATA_ID);
		String retour = "";
		if (instances.size() > 0) {
			URI uri = instances.get(0).getUri();
			retour = restTemplate.getForObject(uri + "/api/v1.0/infosDiscoveryClient", String.class);
		} else {
			retour = "Erreur: pas d'instance pour " + SUPERVISION_DATA_ID;
		}
		return "/infos returned: " + retour;
	}
	
	@RequestMapping("/infos2")
	String restInfos2() throws Exception {
			
		//ParameterizedTypeReference<List<Friend>> reference = new ParameterizedTypeReference<List<Friend>>() { };

		ResponseEntity<String> retour = this.restTemplate.exchange("http://" + SUPERVISION_DATA_ID + "/api/v1.0/infosDiscoveryClient", HttpMethod.GET, null, String.class);
		
		return "/infos2 returned: " + retour.getBody();
	}
	
	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/userByMail")
	private User userByMail(@RequestParam(required = false) String mail) throws Exception {

		//ParameterizedTypeReference<List<Friend>> reference = new ParameterizedTypeReference<List<Friend>>() { };
		
		Optional<User> user = userRepository.findByPrimaryEmailAddress(mail);
		
		return user.orElse(null);
	}
	
}
