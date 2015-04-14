package com.itu.action;

import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import edu.itu.util.Log4jUtil;

@Path("/docmd")
public class CmdAction extends JerseyHtmlAction {

	// public CmdAction() {
	// super(new
	// com.itu.logic.FrontServer2("http://localhost:8080/emsfront/front/frontclientaction"));
	// }

	Logger logger = Log4jUtil.getLogger(CmdAction.class);


	@Override
	public String getUrl() {
		return "/command.jsp";
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

	// @GET
	// public void doCommand() throws IOException {
	// // servletResponse.sendRedirect("/emsfront/command.jsp");
	//
	// logger.debug("setAttributeAndRedirect");
	// try {
	// // doAction();
	// Map<String, String> maps = new HashMap<String, String>();
	//
	// maps.put("username", "zhangsan");
	// maps.put("useremail", "liu@zhangsan.com");
	// setAttributeAndRedirect(maps, "/test.jsp");
	//
	// } catch (ServletException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @POST
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public void doCommand(@FormParam("selCmd") String cmdId,
	// @FormParam("rdiParam") String rdiParam) throws IOException,
	// ServletException {
	// // Contact c = new Contact(id,name,new ArrayList<Address>());
	// // ContactStore.getStore().put(id, c);
	// //
	// // URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
	// // Response.created(uri).build();
	//
	// // do some logic.
	// try {
	// logger.info("cmdId is :" + cmdId + " rdiParam is: " + rdiParam);
	//
	// CloudCommand result = doPostActions(cmdId, rdiParam);
	//
	// logger.info("the result is cloudcommand");
	// logger.info(result.getId());
	// // String url = "..."; // relative url for display jsp page
	// // ServletContext sc = getServletContext();
	// // RequestDispatcher rd = sc.getRequestDispatcher(url);
	//
	// // resultMap.clear();
	// // resultMap.put("result", result);
	// // resultMap.put("checkinfo", rdiParam);
	//
	// setAttributeAndRedirect("/command.jsp");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }

}
