package supervision.server.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import supervision.server.user.User;

@Entity
@Data
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;

	private Role role;

	@OneToOne
	private User user;
	
	protected Account() {}
	
	public Account(String username, String password, Role role, User user) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.user = user;
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
