package com.itu.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import edu.itu.util.ItuStaticUtil;
import edu.itu.util.Log4jUtil;

/**
 * this is abstract Action for Http request and response
 * 
 * @author peter
 *
 */
public abstract class JerseyAction {

	protected Logger logger = Log4jUtil.getLogger(this.getClass());
	protected @Context HttpServletResponse response;
	protected @Context HttpServletRequest request;
	protected @Context UriInfo uriInfo;

	public JerseyAction() {
		initLogic();
	}

	protected void initLogic() {

	}

	public abstract Object doPostAction(Object params) throws Exception;

	public abstract Object convertToPostParams(MultivaluedMap<String, String> queryParameters);

	public abstract Object doGetAction(Object params) throws Exception;

	public abstract Object convertToGetParams(MultivaluedMap<String, String> queryParameters);

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doGet(@Context UriInfo ui) {
		try {
			Object params = convertToGetParams(ui.getQueryParameters());

			Object res = doGetAction(params);

			return ResponseResult(res);

		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			return ResponseResultError(e);
		}
	}

	// @GET
	// @Produces(MediaType.TEXT_HTML)
	// @Path("{root}")
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public void doGetHtml(@PathParam("root") String root) throws Exception {
	// try {
	// response.sendRedirect(root+".jsp");
	// } catch (Exception e) {
	// logger.debug(e.getMessage());
	// throw e;
	// }
	// }

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getResultPost(MultivaluedMap<String, String> form) {
		try {

			Object params = convertToPostParams(form);

			Object res = doPostAction(params);

			return ResponseResult(res);
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			return ResponseResultError(e);
		}
	}

	/**
	 * output error messages.
	 * 
	 * @param e
	 * @return
	 */
	public Response ResponseResultError(Exception e) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(ItuStaticUtil.ERROR_CODE, e.getMessage());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return Response.status(Status.BAD_REQUEST).entity(jo).build();
	}

	/**
	 * response OK
	 * 
	 * @param result
	 * @return
	 */
	protected Response ResponseResult(Object result) {
		return Response.status(Status.OK).entity(result).build();
	}

	// the map used to store some http information
	protected Map<String, Object> resultMap = new HashMap<String, Object>();

	/**
	 * get interval from interval key.
	 * 
	 * @param intervalKey
	 * @return interval
	 */
	protected int getIntervalFromParam(String intervalKey) {
		// TODO Auto-generated method stub
		// return 0;
		int interval;
		switch (intervalKey) {
		case "0":
			interval = 0;
			break;
		case "1h":
			interval = 3600;
			break;
		case "5h":
			interval = 5 * 3600;
			break;
		case "1d":
			interval = 24 * 3600;
			break;
		case "1m":
			interval = 24 * 3600 * 30;
			break;
		case "1w":
			interval = 24 * 3600 * 7;
			break;
		default:
			interval = 3600;
			break;
		}
		return interval;
	}
}
