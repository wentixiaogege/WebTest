package com.itu.action;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class ClientTest {
	@GET
	@Consumes("application/x-protobuf")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		// return "Hello Test";
		// EmsFront
		return Response.status(200).entity("Hello world").build();
	}
}
