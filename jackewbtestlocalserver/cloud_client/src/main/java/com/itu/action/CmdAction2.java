package com.itu.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.itu.logic.FrontServer3;
import com.itu.util.UrlUtil;

import edu.itu.util.Log4jUtil;

@Path("/docmd2")
public class CmdAction2 extends JerseyHtmlAction {
	//
	// public CmdAction2() {
	// super(new
	// com.itu.logic.FrontServer3("http://localhost:8080/emsfront/front/frontclientaction2"));
	// }

	Logger logger = Log4jUtil.getLogger(CmdAction2.class);
	FrontServer3 clientWebLogic;

	@Override
	protected void initLogic() {
		clientWebLogic = new FrontServer3(UrlUtil.URLDOCOMMAND, HttpMethod.POST);
	}

	@GET
	public void doCommand() throws IOException {

		logger.debug("setAttributeAndRedirect");
		try {
			// doAction();
			Map<String, String> maps = new HashMap<String, String>();

			maps.put("username", "zhangsan");
			maps.put("useremail", "liu@zhangsan.com");
			setAttributeAndRedirect(maps, "/test.jsp");

		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void doCommand(@FormParam("selCmd") String cmdId, @FormParam("rdiParam") String rdiParam) throws IOException, ServletException {
		// Contact c = new Contact(id,name,new ArrayList<Address>());
		// ContactStore.getStore().put(id, c);
		//
		// URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		// Response.created(uri).build();

		// do some logic.
		try {
			logger.info("cmdId is :" + cmdId + " rdiParam is: " + rdiParam);

			// String result = doPostActions(cmdId, rdiParam);

			String result = clientWebLogic.executeLogic(new String[] { cmdId, rdiParam });

			logger.info("the result is :" + result);
			// String url = "..."; // relative url for display jsp page
			// ServletContext sc = getServletContext();
			// RequestDispatcher rd = sc.getRequestDispatcher(url);

			resultMap.clear();
			resultMap.put("result", result);
			resultMap.put("checkinfo", rdiParam);

			setAttributeAndRedirect("/command.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doPostAction(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object convertToPostParams(MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doGetAction(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object convertToGetParams(MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		return null;
	}
}
