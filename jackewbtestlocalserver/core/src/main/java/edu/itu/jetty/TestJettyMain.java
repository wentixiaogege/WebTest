package edu.itu.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

import edu.itu.localrest.LightManipulation;
import edu.itu.localrest.LightOperation;

public class TestJettyMain {

	// public static Controller c = new Controller();

//	public static ThreadClass tc = new ThreadClass();

	public static LightThreadTest lightthreadtest = new LightThreadTest(); 
	public static void main(String[] args) throws Exception {

		
		ServletHolder sh = new ServletHolder(ServletContainer.class);
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages", "edu.itu.localrest");// Set
		// the package where the services reside
		sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		// sh.setInitParameter("jersey.config.server.provider.classnames",
		// TestJettyAction.class.getCanonicalName());
		// sh.setInitParameter("jersey.config.server.provider.classnames",
		// TestJettyAction2.class.getCanonicalName());

		// registAction to ta3.
		// TestJettyAction3 ta3 = new TestJettyAction3();

		// ta3.registAction(tc);

		sh.setInitParameter("jersey.config.server.provider.classnames", LightManipulation.class.getCanonicalName());
		sh.setInitParameter("jersey.config.server.provider.classnames", LightOperation.class.getCanonicalName());
		Server server = new Server(9999);
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
		context.addServlet(sh, "/*");
		server.start();

		lightthreadtest.run();
		// very important! jetty can not compatible with java8
		// Thread thread = new Thread() {
		// public void run() {
		// while (true) {
		// System.out.println(number);
		// try {
		// TimeUnit.SECONDS.sleep(1);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// };
		// thread.start();

		server.join();

	};
}
