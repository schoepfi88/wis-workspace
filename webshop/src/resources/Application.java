package resources;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
@ApplicationPath(value = "/api")
public class Application extends ResourceConfig{
	public Application(){
		packages("resources,org.codehaus.*");
		register(ServletContainer.class);
		register(JacksonFeature.class);
	}
}
