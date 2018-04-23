package supervision.server.config.feign;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FeignGlobalControllerAdvice {

	@ExceptionHandler(FeignClientException.class)
	public ResponseEntity<FeignClientException> handleException(FeignClientException exception,
			HttpServletRequest req) {
		switch (exception.getStatus()) {
		case 401:
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		case 403:
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		case 404:
			return ResponseEntity.notFound().build();
		default:
			return ResponseEntity.status(exception.getStatus()).build();
		}
	}

	@ExceptionHandler(FeignServerException.class)
	public ResponseEntity<FeignServerException> handleException(FeignServerException exception,
			HttpServletRequest req) {
		return ResponseEntity.status(exception.getStatus()).build();
	}

}
