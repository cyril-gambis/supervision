package supervision.server.supervisor.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import supervision.server.supervisor.Supervisor;

@RepositoryRestResource
@SwaggerDefinition(
        info = @Info(
                description = "My Supervision User API",
                version = "V1.0.0",
                title = "The Supervision API for the Supervision app",
                termsOfService = "share and care",
                contact = @Contact(name = "Support of supervision server", email = "support@supervision-server.supervision-server", url = "http://supervision-home/index.html"),
                license = @License(name = "Apache 2.0", url = "http://www.apache.org")
                ),
        consumes = {"application/json" },
        produces = {"application/json" },
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        externalDocs = @ExternalDocs(value = "About me", url = "http://about.me/me")
)
public interface SupervisorRepository extends PagingAndSortingRepository<Supervisor, Long> {

	/**
	 * Example of call:
	 * http://<server>:<port>/supervisors/search/findByUsername?username=user@test.com
	 * 
	 * @Api annotations for Swagger auto API doc generation
	 * 
	 * @Param is optional (default value)
	 * @RequestMapping is also optional (default)
	 * 
	 * @date 16/04/2018
	 * @author Cyril Gambis
	 * @param username
	 * @return
	 */
	@ApiOperation(value = "Search a user by its first name", response = Supervisor.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved user"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@RequestMapping(produces = "application/json")
	public Supervisor findByUsername(@Param("username") String username);

}

/* Exemple de filtrage et methode controller

@RequestMapping(path="/user/{id}", method = RequestMethod.GET)
public ResponseEntity  listUser(@PathVariable(value = "id") String id){
	return new ResponseEntity(getUsers().stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null), HttpStatus.OK);
	
}

*/