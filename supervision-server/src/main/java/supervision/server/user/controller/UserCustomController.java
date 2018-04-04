package supervision.server.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import supervision.server.user.User;
import supervision.server.user.repository.UserRepository;

/**
 * IMPORTANT: if you use @RepositoryRestController, you must put a @RequestMapping,
 * otherwise your controllers will not be created and accessing them will result in Error 404
 * 
 * @date 25/09/2017
 * @author Cyril Gambis
 */
@RepositoryRestController
@RequestMapping(path="/users-custom")
public class UserCustomController {

	@Autowired
	private UserRepository userRepository;
	
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
	public User createUser(@RequestParam(required = true) String name, @RequestParam(required = true) String lastName) {
		User newUser = new User(name, lastName);
		User theCreatedUser = userRepository.save(newUser);
		return theCreatedUser;
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

}
