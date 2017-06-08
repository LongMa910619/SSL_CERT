package com.key.SSL_Cert;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.vaadin.server.VaadinServlet;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server;
		try {
			server = start(8080);
			server.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Server start(int port) throws Exception {
		Server server = new Server(port);
		
		ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		contextHandler.setContextPath("/");
		
		ServletHolder sh = new ServletHolder(new VaadinServlet());
		contextHandler.addServlet(sh, "/*");
		//contextHandler.setInitParameter("Resources", "http://virit.in/dawn/11");
		contextHandler.setInitParameter("ui", keyGen.class.getCanonicalName());
		//contextHandler.setInitParameter("widgetset", "com.vaadin.addon.touchkit.gwt.TouchKitWidgetSet");
		//contextHandler.setInitParameter("org.eclipse.jetty.servlet.Default.gzip", "false");
		//sh.setInitParameter("UIProvider", keyGen.class.getName());
		
		// Register cdn.virit.in if present
		/*try {
			Class cls = Class.forName("in.virit.WidgetSet");
			if (cls != null) {
				contextHandler.getSessionHandler().addEventListener((EventListener)cls.newInstance());
			}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}*/
		
		server.setHandler(contextHandler);
		server.start();
		return server;
	}
}
