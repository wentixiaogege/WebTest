package edu.itu.localserverclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import edu.itu.jacktest.Student;
 
public class XMLClient {
public static void main(String[] args) {
	
	Client client = Client.create();
	WebResource webResource = client.resource("http://localhost:9999/xmlServices/student/James");
	ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
	if (response.getStatus() != 200) {
	throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
	}
	
	//String output = response.getEntity(String.class);
	Student output = response.getEntity(Student.class); //Get the object from the response
	System.out.println("Output xml client .... \n");
	System.out.println(output);
}
} 