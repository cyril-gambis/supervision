package supervision.server.usagelog;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class LastAccess {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long customerId;
	private Long userId;
	private LocalDateTime lastAccessDate;
	private LocalDateTime lastCalculation;
	private String firstName;
	private String lastName;

	public LastAccess() { }
	public LastAccess(Long customerId, Long userId, LocalDateTime lastAccessDate, LocalDateTime lastCalculation,
			String firstName, String lastName) {
		this.customerId = customerId;
		this.userId = userId;
		this.lastAccessDate = lastAccessDate;
		this.lastCalculation = lastCalculation;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
