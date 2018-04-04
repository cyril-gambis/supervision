package supervision.server.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	@ApiModelProperty(notes = "The first name of the user")
	public String firstName;
	
	public String lastName;

	protected User() {}
	
	public User(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
    public String toString() {
		return String.format(
                "User[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
