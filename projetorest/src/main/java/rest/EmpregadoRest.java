package rest;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.swagger.annotations.Api;

@Api
@Path("/empregado")
public class EmpregadoRest {

        static private HashMap<String, Empregado> empregados = new HashMap<String, Empregado>();

        @GET
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response obterEmpregado(@PathParam("id") String id) {
                if (empregados.containsKey(id)) {
                        return Response.ok().entity(empregados.get(id)).build();
                } else {
                        return Response.status(404).build();
                }
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response listarEmpregados() {
                return Response.ok().entity(empregados).build();
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response incluirEmpregado(Empregado emp) {
                UUID uuid = UUID.randomUUID();
                emp.setId(uuid.toString());
                empregados.put(uuid.toString(), emp);
                return Response.ok(emp).build();
        }

        @PUT
        @Path("{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response alterarEmpregado(@PathParam("id") String id, Empregado emp) {
                if (empregados.containsKey(id)) {      
                        emp.setId(id);
                        empregados.put(id, emp);
                        return Response.ok().entity(emp).build();
                } else {
                        return Response.status(404).build();
                }
        }

        @DELETE
        @Path("{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response excluirEmpregado(@PathParam("id") String id) {
                if (empregados.containsKey(id)) {                      
                        empregados.remove(id);
                        return Response.ok().build();
                } else {
                        return Response.status(404).build();
                }
        }

}