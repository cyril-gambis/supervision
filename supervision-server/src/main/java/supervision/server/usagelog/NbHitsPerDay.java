package supervision.server.usagelog;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class NbHitsPerDay {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long customerId;
	private Integer day;
	private Integer month;
	private Integer year;
	private Long nbHits;
	private Long pageId;
	private LocalDateTime calculationDate;

	public NbHitsPerDay() {}
	
	public NbHitsPerDay(Long customerId, Integer day, Integer month, Integer year,
			Long nbHits, Long pageId, LocalDateTime calculationDate) {
		this.customerId = customerId;
		this.day = day;
		this.month = month;
		this.year = year;
		this.nbHits = nbHits;
		this.pageId = pageId;
		this.calculationDate = calculationDate;
	}

}
