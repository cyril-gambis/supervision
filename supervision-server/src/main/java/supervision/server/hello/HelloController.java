package supervision.server.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping(value="/hello")
	@ResponseBody
	public String index(@RequestParam(required = false) String name) {
		if (name != null) {
			return "Hello " + name;
		} else {
			return "Hello Guest";
		}
	}
	
}
