package supervision.server.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;

	private Role role;

	protected Account() {}
	
	public Account(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
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
}
