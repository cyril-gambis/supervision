package supervision.server.user.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import supervision.server.user.User;

@FeignClient("SUPERVISION-DATA/api/v1.0")
public interface UserClient {

	@RequestMapping(method = RequestMethod.GET, value = "/users/search/findByUsername", consumes = "application/json")
	User findByMail(@RequestParam String username);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/search/findByPrimaryEmailAddress", consumes = "application/json")
	User findByPrimaryEmailAddress(@RequestParam String emailAddress);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}", consumes = "application/hal+json")
	User findById(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users", consumes = "application/json")
	List<User> findAll();

	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}?projection=usermail", consumes = "application/json")
	User findByIdWithMail(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/search/findByCustomerId", consumes = "application/json")
	Resources<User> findByCustomerId(@RequestParam Long customerId);
	
}
