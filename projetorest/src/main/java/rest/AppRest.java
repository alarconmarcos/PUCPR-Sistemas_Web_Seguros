package rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@ApplicationPath("/rest")
public class AppRest extends Application {

        public AppRest() {
                BeanConfig conf = new BeanConfig();
                conf.setTitle("Projeto REST");
                conf.setDescription("Projeto REST");
                conf.setVersion("1.0.0");
                conf.setHost("localhost:8080");
                conf.setBasePath("/projetorest/rest");
                conf.setSchemes(new String[] { "http" });
                conf.setResourcePackage("rest");
                conf.setScan(true);
        }

        @Override
        public Set<Class<?>> getClasses() {
                Set<Class<?>> resources = new HashSet<>();             
                resources.add(EmpregadoRest.class);

                // classes do swagger...
                resources.add(ApiListingResource.class);
                resources.add(SwaggerSerializers.class);
                return resources;
        }

}