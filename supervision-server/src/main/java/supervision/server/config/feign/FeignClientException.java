package supervision.server.config.feign;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FeignClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int status;
	private String reason;

	public FeignClientException(int status, String reason) {
		super();
		this.status = status;
		this.reason = reason;
	}
}
