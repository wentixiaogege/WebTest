package com.itu.jetty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * the class must be public class
 * 
 * @author gqq
 *
 */
@Path("/controller")
public class Controller {

	public Controller() {
		registAction(TestJettyMain.tc);
	}

	public interface IAciton {
		void doAction(String param);
	}

	IAciton action;

	public void registAction(IAciton action) {
		this.action = action;
	}

	public void doControl(String param) {
		if (action != null) {
			action.doAction(param);
		}
	}

	// private int num;
	//
	// public TestJettyAction3() {
	// this.num = 1;
	// }
	@GET
	@Path("add/{param}")
	@Produces(MediaType.TEXT_PLAIN)
	public String test(@PathParam("param") String param) {
		// Controller c = new Controller();
		// TestJettyAction.c.registAction(TestJettyAction.tc);
		// TestJettyAction.c.doControl();
		doControl(param);
		return "" + "ok";
	}
}

