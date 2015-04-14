
package edu.itu.localrest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import edu.itu.html.JSONResult;
import edu.itu.html.LocalServerLightSearchOperation;
import edu.itu.html.LocalServerLightSetOperation;
import edu.itu.jetty.LightLogic;
import edu.itu.util.Log4jUtil;

/**
 * the class must be public class
 * @author gqq
 */
@Path("/LightOperation")
public class LightOperation {
	
	LightLogic lightlogic = new LightLogic();
	Logger logger = Log4jUtil.getLogger(LightOperation.class);
	

	@GET
	@Path("LocalServerLightSearch")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetLightState() {

		List<LocalServerLightSearchOperation> result =null;
		try {
			
			logger.info("LocalServerLightSearchState");
			result = lightlogic.executeLightSearchAction();
			logger.info("the result is :" + result);

		} catch (Exception e) {
			e.printStackTrace();
			// better for debugging
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(e.getMessage()+e.getStackTrace()).build();
		}
		/*
		 * query the db here
		 */
		return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
	}
	
	
	@POST
	@Path("LocalServerLightsetMaxAndMin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doLightsetMaxAndMin(LocalServerLightSetOperation t ) {

//		boolean result =false;
		JSONResult result = new JSONResult(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "failed");
		try {
			
			logger.debug("LocalServerLightsetMaxAndMin");
			logger.info("LocalServerLightsetMaxAndMin");
			lightlogic.executeLightSetAction(t);
			result.setHttpcode(Response.Status.OK.getStatusCode());
			result.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(result).build();
	}
}

