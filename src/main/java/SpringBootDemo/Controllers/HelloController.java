package SpringBootDemo.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="HelloController API", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class HelloController {

	@Value("${message}")
	private String message;
	
	@ApiOperation("Returns the Hello World Page")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=String.class)
			})

	@GetMapping("/hello")
	public String sayHello()
	{
		return message;
	}
	
	
}
