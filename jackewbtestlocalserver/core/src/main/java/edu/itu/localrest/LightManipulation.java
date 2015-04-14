package edu.itu.localrest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import edu.itu.jetty.LightLogic;
import edu.itu.jetty.TestJettyMain;

import edu.itu.html.JSONResult;
import edu.itu.html.LocalServerLightManipulation;
import edu.itu.util.Log4jUtil;

/**
 * the class must be public class
 * 
 * @author gqq
 *
 */
@Path("/LightManipulation")
public class LightManipulation {
	
	private ILightManipulationHandler mLightManipulationHandler;
	LightLogic lightlogic;
	Logger logger = Log4jUtil.getLogger(LightManipulation.class);
	
	public LightManipulation() {
		
		this.mLightManipulationHandler = TestJettyMain.lightthreadtest;
		
//		registAction(TestJettyMain.lightthreadtest);
	}

	public interface ILightManipulationHandler {
		void handle(LocalServerLightManipulation param);
	}

	public void registAction(ILightManipulationHandler handler) {
		this.mLightManipulationHandler = handler;
	}

	public void doLightManipulation(LocalServerLightManipulation param) {
		if (mLightManipulationHandler != null) {
			
			
			mLightManipulationHandler.handle(param);
		}
	}

	@POST
	@Path("/LocalServerLightManipulation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPostControl(LocalServerLightManipulation t) {

		JSONResult result = new JSONResult(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "failed");
		try {
			logger.debug("testhere");
			doLightManipulation(t);
			result.setHttpcode(Response.Status.OK.getStatusCode());
			result.setResult("success");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return Response.status(200).entity(result).build();
	}
}

