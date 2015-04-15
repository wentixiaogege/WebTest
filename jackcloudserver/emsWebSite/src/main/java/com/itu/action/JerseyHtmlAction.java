package com.itu.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * this is abstract Action for Http request and response
 * 
 * @author peter
 *
 */
public abstract class JerseyHtmlAction extends JerseyAction {

	public abstract String getUrl();

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void doGetHtml(@Context UriInfo ui) throws Exception {
		try {

			Object params = convertToGetParams(ui.getQueryParameters());

			Object res = doGetAction(params);

			String url = getUrl();

			setAttributeAndRedirect(res, url);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void doPostHtml(MultivaluedMap<String, String> form) throws Exception {
		try {

			Object params = convertToPostParams(form);

			Object res = doPostAction(params);

			String url = getUrl();

			setAttributeAndRedirect(res, url);
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * this method is used to set attributes to request and redirect to a jsp
	 * file
	 * 
	 * @param attributes
	 *            the infomation to be stored
	 * @param url
	 *            the url to be redirected.
	 * @throws ServletException
	 * @throws IOException
	 */
	@Produces(MediaType.TEXT_HTML)
	protected void setAttributeAndRedirect(Object attributes, String url) throws ServletException, IOException {
		request.setAttribute("ITU_HTML_RESPONSE", attributes);
		request.getRequestDispatcher(url).forward(request, response);
	}

	@Produces(MediaType.TEXT_HTML)
	protected void setAttributeAndRedirect(String url) throws ServletException, IOException {
		setAttributeAndRedirect(resultMap, url);
	}

}
