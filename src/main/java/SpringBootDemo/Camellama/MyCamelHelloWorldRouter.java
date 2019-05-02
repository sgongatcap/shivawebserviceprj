package SpringBootDemo.Camellama;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import SpringBootDemo.Beans.Person;

@Component
public class MyCamelHelloWorldRouter extends RouteBuilder{
	
	@Autowired
	public MyFilter myfilter;
	
	  @Override 
	  public void configure() throws Exception 
	  {
		  restConfiguration()
		  .component("servlet")
		  .enableCORS(true)
		  .bindingMode(RestBindingMode.json);

		  rest().post("/pello")
		  .consumes("application/json")
		  .to("direct:pello"); 		
	  
		 from("direct:pello")	
         .filter().method(myfilter, "filter")
         .log(LoggingLevel.INFO, "Inside Filter: ${body}")
         .log(LoggingLevel.INFO, "Hello World Date After: ${header.myDate}")						
         .log(LoggingLevel.INFO, "Hello World Date: ${date:header.myDate:yyyy-MM-dd HH:mm:ss}")		
         .to("direct:jillo")
         .end()
         
		.log(LoggingLevel.INFO, "Transform Simple: ${body}")
		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
			//.convertBodyTo(ArrayList.class)
			//.setBody(simple("${body}.replaceAll('=',':')"))
			//.transform().body(ArrayList.class)  //Not needed as this is automatic if json array is detected
			//.split(body().method("iterator"))
			//.log(LoggingLevel.INFO, "Iterator: ${body}")
			//.end()
			//.transform().simple("${body}")
			.transform(simple("Hello ${body}"));
		 
		 from("direct:jillo")	
		 .log(LoggingLevel.INFO, "Jillo Body: ${body}");

		}

}
