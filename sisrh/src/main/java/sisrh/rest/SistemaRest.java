package sisrh.rest;

import java.util.*;
import java.text.*; 
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.swagger.annotations.*;

@Api
@Path("/sistema")
public class SistemaRest {

	@GET
	@Path("ping")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		UUID uuid = UUID.randomUUID();
		return Response.ok().entity("pong: " + uuid).build();
	}

	@GET
	@Path("datahora")
	@Produces(MediaType.TEXT_PLAIN)
	public Response datahora() {
		String pattern = "dd/MM/YYYY - HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return Response.ok().entity(simpleDateFormat.format(new Date())).build();
	}

}

