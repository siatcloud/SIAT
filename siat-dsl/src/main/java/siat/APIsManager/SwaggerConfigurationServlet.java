package siat.APIsManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.ext.RuntimeDelegate;

import io.swagger.jaxrs.config.BeanConfig;

public class SwaggerConfigurationServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		BeanConfig beanConfig = new BeanConfig();
		
		beanConfig.setBasePath("siat/app");
		beanConfig.setHost("localhost:8181");
		//beanConfig.setHost("163.180.116.242:8181");
		beanConfig.setResourcePackage("siat.dsl");
		beanConfig.setPrettyPrint(true);
		beanConfig.setScan(true);
		beanConfig.setSchemes(new String[] {"http"});
		beanConfig.setVersion("1.0");
		beanConfig.setTitle("SIAT App Swagger Docs");
		
		//http://localhost:8181/siat/app
		
		
		//This line is added becouse the following error. 
		//https://stackoverflow.com/questions/43931240/jersey-spark-javax-ws-rs-core-uribuilder-uri
		RuntimeDelegate.setInstance(new org.glassfish.jersey.internal.RuntimeDelegateImpl());
	}

}
