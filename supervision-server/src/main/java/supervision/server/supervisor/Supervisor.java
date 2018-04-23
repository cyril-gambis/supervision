package supervision.server.supervisor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
public class Supervisor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;

	@ApiModelProperty(notes = "The first name of the user")
	private String firstName;
	private String lastName;
	
	private Role role;
/*
	@OneToOne
	private Preference preference;
*/
	protected Supervisor() {}
	
	public String getSpringSecurityRole() {
		switch (this.role) {
		case ADMIN:
			return "ADMIN";
		case USER:
			return "USER";
		default:
				return "";
		}
	}

	public Supervisor(String username, String password, String firstName, String lastName, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}
}
