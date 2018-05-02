package supervision.server.usagelog;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class NbHitsPerMonth {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long customerId;
	private Long userId;
	private Integer month;
	private Integer year;
	private Long nbHits;
	private Long pageId;
	private LocalDateTime calculationDate;

	public NbHitsPerMonth() {}
	
	public NbHitsPerMonth(Long customerId, Long userId, Integer month, Integer year,
			Long nbHits, Long pageId, LocalDateTime calculationDate) {
		this.customerId = customerId;
		this.userId = userId;
		this.month = month; 
		this.year = year;
		this.nbHits = nbHits;
		this.pageId = pageId;
		this.calculationDate = calculationDate;
	}

}
