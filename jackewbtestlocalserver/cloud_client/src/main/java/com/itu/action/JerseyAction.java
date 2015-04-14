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
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import edu.itu.util.Log4jUtil;

/**
 * this is abstract Action for Http request and response
 * 
 * @author peter
 *
 */
public abstract class JerseyAction {

	Logger logger = Log4jUtil.getLogger(JerseyAction.class);
	protected @Context HttpServletResponse response;
	protected @Context HttpServletRequest request;
	protected @Context UriInfo uriInfo;

	public JerseyAction() {
		initLogic();
	}

	protected void initLogic() {

	}

	// front logic
	// protected FrontLogicForWeb<? ,?, ?> clientWebLogic;
	// protected FrontLogic<?, ?> clientLogic;

	public abstract Object doPostAction(Object params);

	public abstract Object convertToPostParams(MultivaluedMap<String, String> queryParameters);

	public abstract Object doGetAction(Object params);

	public abstract Object convertToGetParams(MultivaluedMap<String, String> queryParameters);

	// @GET

	// @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	// public List<?> getLists() {
	// return null;
	// }
	//
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doGet(@Context UriInfo ui) {
		Object params = convertToGetParams(ui.getQueryParameters());

		Object res = doGetAction(params);

		return ResponseResult(res);
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getResultPost(MultivaluedMap<String, String> form) {
		Object params = convertToPostParams(form);

		Object res = doPostAction(params);

		return ResponseResult(res);
	}

	// the map used to store some http information
	protected Map<String, Object> resultMap = new HashMap<String, Object>();

	protected Response ResponseResult(Object result) {
		return Response.status(200).entity(result).build();
	}

	// public String[] getParams(MultivaluedMap<String, String> queryParameters)
	// {
	// String[] ss = new String[queryParameters.size()];
	//
	// int i = 0;
	// for (Entry<String, List<String>> entry : queryParameters.entrySet()) {
	// String key = entry.getKey();
	// List<String> values = entry.getValue();
	// ss[i++] = values.get(0);
	// }
	// return ss;
	// }

	//
	// @GET
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public Response doGet(@Context UriInfo ui) {
	// // V result = commonLogic.executeAction();
	// return ResponseResult(result);
	// }
	//
	// @GET
	// @Path("param")
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public Response doGetParam(@Context UriInfo ui) {
	// if (null != uriInfo) {
	// logger.debug("uriInfo is not null");
	// }
	// T t = getT();
	// V result = commonLogic.executeAction(t);
	// return ResponseResult(result);
	// }
	//
	// @POST
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public Response doPost(@Context UriInfo ui) {
	// V result = commonLogic.executeAction();
	// return ResponseResult(result);
	// }
	//
	// @POST
	// @Path("param")
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public Response doPostParam(@Context UriInfo ui) {
	// if (null != uriInfo) {
	// logger.debug("uriInfo is not null");
	// }
	// T t = getT();
	// V result = commonLogic.executeAction(t);
	// return ResponseResult(result);
	//
	// }

	// {
	// MultivaluedMap<String, String> queryParameters = ui.getQueryParameters();
	//
	// String[] ss = getParams(queryParameters);
	//
	// boolean result = commonLogic.executeAction(ss);
	//
	// if (result) {
	// return Response.ok().entity(ReturnResult.TRUE.toString()).build();
	// }
	// return Response.ok().entity(ReturnResult.FALSE.toString()).build();
	// }

	// private T getT() {
	// return null;
	// }

	// {
	// MultivaluedMap<String, String> queryParameters = ui.getQueryParameters();
	//
	// String[] ss = getParams(queryParameters);
	//
	// boolean result = commonLogic.executeActionBool(ss);
	//
	// if (result) {
	// return Response.ok().entity(ReturnResult.TRUE.toString()).build();
	// }
	// return Response.ok().entity(ReturnResult.FALSE.toString()).build();
	// }
}
