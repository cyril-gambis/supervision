package supervision.server.config.feign;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FeignServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int status;
	private String reason;
	
	public FeignServerException(int status, String reason) {
		super();
		this.status = status;
		this.reason = reason;
	}
}
