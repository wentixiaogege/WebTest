package com.itu.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class TestJettyMain {

	// public static Controller c = new Controller();

	public static ThreadClass tc = new ThreadClass();

	public static void main(String[] args) throws Exception {

		ServletHolder sh = new ServletHolder(ServletContainer.class);
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages", "com.itu.jetty");// Set
		// the package where the services reside
		sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

		sh.setInitParameter("jersey.config.server.provider.classnames", Controller.class.getCanonicalName());
		Server server = new Server(9999);
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
		context.addServlet(sh, "/*");
		server.start();
		//start new thread.
//		tc.start();

		server.join();

	};
}
