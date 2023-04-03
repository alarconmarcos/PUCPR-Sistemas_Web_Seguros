package rbac.rest;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import io.swagger.jaxrs.config.*;
import io.swagger.jaxrs.listing.*;


@ApplicationPath("/rest")
public class AppRest extends Application {

	public AppRest() {
		BeanConfig conf = new BeanConfig();
		conf.setTitle("RBAC");
		conf.setDescription("Sistema de Gestão de Identidade e Acesso");
		conf.setVersion("1.0.0");
		conf.setHost("localhost:8080");
		conf.setBasePath("/rbac/rest");
		conf.setSchemes(new String[] { "http" });
		conf.setResourcePackage("rbac.rest");
		conf.setScan(true);
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<>();		
		resources.add(LoginUnicoRest.class);
		
		resources.add(ApiListingResource.class);
		resources.add(SwaggerSerializers.class);
		return resources;
	}

}
