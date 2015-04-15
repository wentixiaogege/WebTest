package com.itu.jetty;

import static java.lang.System.out;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.itu.bean.SmartMeterDataQuery;

import edu.itu.util.Log4jUtil;

@Path("/lightcontroller")
public class LightController {
	protected Logger logger = Log4jUtil.getLogger(this.getClass());

	@GET
	@Path("gett")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getLightControllInfo(String controller) {
		logger.debug("controller:" + controller);
		out.println("controller:" + controller);
		JSONObject joRes = new JSONObject();
		try {
			joRes.put("result", "ok");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.println("response ok");
		return Response.status(Status.OK).entity(joRes).build();
	}
	
	@POST
	@Path("postt")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postLightControllInfo(String controller) {
		logger.debug("controller:" + controller);
		out.println("post controller:" + controller);
		JSONObject joRes = new JSONObject();
		try {
			joRes.put("post result", "ok");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.println("post response ok");
		return Response.status(Status.OK).entity(joRes).build();
	}
	
	@POST
	@Path("qsmart")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postSmartMeterQuery(SmartMeterDataQuery query) {
//		logger.debug("controller:" + controller);
		out.println("post controller:" + query.toString());
		JSONObject joRes = new JSONObject();
		try {
			joRes.put("post result", "ok");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.println("post response ok");
		return Response.status(Status.OK).entity(joRes).build();
	}
}
