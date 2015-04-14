package edu.itu.localserverclient;

import java.util.List;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import edu.itu.html.LocalServerLightSearchOperation;
 
 
public class JsonClient {
 
public static void main(String[] args) {
   /* Client client = Client.create();
	//Get
	WebResource webResource = client.resource("http://localhost:9999/employee/getEmployee");
	ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
	if (response.getStatus() != 200) {
	throw new RuntimeException("Failed : HTTP error code : "
	+ response.getStatus());
	}
// String output = response.getEntity(String.class);
	Employee output = response.getEntity(Employee.class);//Get the object from the response
	System.out.println("Output json client .... \n");
	System.out.println(output);
	//Post
	webResource = client.resource("http://localhost:9999/employee/postEmployee");
	webResource.accept("application/json").post(ClientResponse.class, output);*
	*/
	//Post data to control the specific light
    
	/*Client client = Client.create();
	
	LocalServerLightManipulation manipulation = new LocalServerLightManipulation();
	manipulation.setCoordinator_id(1);
	manipulation.setSm_id(1);
	manipulation.setLoad_id(1);
	manipulation.setLoad_config_id(1); 
	manipulation.setManipulation(0);
	WebResource webResource = client.resource("http://localhost:9999/LightManipulation/LocalServerLightManipulation");
	ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, manipulation);
	
	JSONResult jsonObject = response.getEntity(JSONResult.class);
	// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	System.out.println(jsonObject);
	if (response.getStatus() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ response.getStatus());
		}*/
	//JSONResult object for the result
	
	/*// set max and min value to the monitor control table.
	Client client = Client.create();
		
    LocalServerLightSetOperation setOperation = new LocalServerLightSetOperation();
    
	setOperation.setCoordinator_id(1);
	setOperation.setSm_id(1);
	setOperation.setLoad_id(1);
	setOperation.setLoad_config_id(1);
	setOperation.setMax_value(100);
	setOperation.setMin_value(-10);
	WebResource webResource = client.resource("http://localhost:9999/LightOperation/LocalServerLightsetMaxAndMin");
	ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, setOperation);
	if (response.getStatus() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ response.getStatus());
		}
	JSONResult jsonObject = response.getEntity(JSONResult.class);
	// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	System.out.println(jsonObject);*/
	
	
//	Client client = Client.create();
	ClientConfig cfg = new DefaultClientConfig();
	cfg.getClasses().add(JacksonJsonProvider.class);
	Client client = Client.create(cfg);
	
	WebResource webResource = client.resource("http://localhost:9999/LightOperation/LocalServerLightSearch");
	ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
	
	if (response.getStatus() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ response.getStatus());
		}

	GenericType<List<LocalServerLightSearchOperation>> type = new GenericType<List<LocalServerLightSearchOperation>>(){};
	List<LocalServerLightSearchOperation> getdatalist = response.getEntity(type);
	
	// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
//	System.out.println(jsonObject);
	
	for (LocalServerLightSearchOperation localServerLightSearchOperation : getdatalist) {
		
		System.out.print(localServerLightSearchOperation.toString());
	}
	
	
	}
} 
