package supervision.server.config.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();
	
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() >= 400 && response.status() <= 499) {
            return new FeignClientException(
                    response.status(),
                    response.reason()
            );
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new FeignServerException(
                    response.status(),
                    response.reason()
            );
        }
        return defaultErrorDecoder.decode(methodKey, response);
	}

}
