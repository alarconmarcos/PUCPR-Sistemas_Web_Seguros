package sisrh.rest;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Usuario;

@Api
@Path("/usuario")
public class UsuariosRest {	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarUsuarios() throws Exception {
		List<Usuario> lista = Banco.listarUsuarios();		
		GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(lista) {};
		return Response.ok().entity(entity).build();
	}


	@GET
	@Path("{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterEmpregado(@PathParam("matricula") String matricula) throws Exception {
		try {
			Usuario usuario = Banco.buscarUsuarioPorMatricula(matricula);
			if ( usuario != null ) {
				return Response.ok().entity(usuario).build();
			}else {
				return Response.status(Status.NOT_FOUND)
						.entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
			}
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha para obter usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}
	}
	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response incluirUsuario(Usuario usuario) {
		try {
			Usuario usu = Banco.incluirUsuario(usuario);
			return Response.ok().entity(usu).build();
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na inclusao do usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}		
	}
	
	@PUT	
	@Path("{matricula}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarUsuario(@PathParam("matricula") String matricula, Usuario usuario)  {
		try {			
			if ( Banco.buscarUsuarioPorMatricula(matricula) == null ) {				
				return Response.status(Status.NOT_FOUND)
						.entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
			}				
			Usuario usu = Banco.alterarUsuario(matricula, usuario);	
			return Response.ok().entity(usu).build();
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na alteracao do usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}
	}

	@DELETE
	@Path("{matricula}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirUsuario(@PathParam("matricula") String matricula) throws Exception {
		try {
			if ( Banco.buscarUsuarioPorMatricula(matricula) == null ) {				
				return Response.status(Status.NOT_FOUND).
						entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
			}				
			Banco.excluirUsuario(matricula);
			return Response.ok().entity("{ \"mensagem\" : \"Usuario "+ matricula + " excluido!\" }").build();	
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity("{ \"mensagem\" : \"Falha na exclusao do usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}		
	}


}
