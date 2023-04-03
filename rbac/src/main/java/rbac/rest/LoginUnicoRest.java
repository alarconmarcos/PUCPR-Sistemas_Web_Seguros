package rbac.rest;

import java.security.Key;
import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.Api;
import rbac.dto.*;

@Api
@Path("/loginunico")
public class LoginUnicoRest {
	
	private static Key chavePrivada = null;
	private static Map<String, Map<String, String>> rbac = new HashMap<>();

	static {
		inicializarRegrasRBAC();
	}

	private static void inicializarRegrasRBAC() {
		Map<String, String> perfil_admin = new HashMap<>();
		perfil_admin.put("empregado", "GET,POST,PUT,DELETE");
		perfil_admin.put("usuario", "GET,POST,PUT,DELETE");
		perfil_admin.put("sistema", "GET");

		Map<String, String> perfil_user = new HashMap<>();
		perfil_user.put("empregado", "GET");
		perfil_user.put("sistema", "GET");

		Map<String, String> perfil_gestor = new HashMap<>();
		perfil_gestor.put("empregado", "GET,POST,PUT,DELETE");
		perfil_gestor.put("usuario", "GET");
		
		rbac.put("ADMIN", perfil_admin);
		rbac.put("USER", perfil_user);
		rbac.put("GESTOR", perfil_gestor);
	}
	

	private static Key getPrivateKey() {
		if (chavePrivada == null) {
			String privateKey = "wb8w338e24f11f4692a95738fe2e893c2ab8338e24f11f4e64";
			byte[] keyBytes = Decoders.BASE64.decode(privateKey);
			chavePrivada = Keys.hmacShaKeyFor(keyBytes);
		}
		return chavePrivada;
	}

	private static Jws<Claims> validarToken(String tokenJWT) throws Exception {
		try {
			Jws<Claims> declaracoes = Jwts.parserBuilder()
					.setSigningKey(getPrivateKey())
					.build()
					.parseClaimsJws(tokenJWT);
			return declaracoes;
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("Token expirado!");
		} catch (MalformedJwtException ex) {
			throw new RuntimeException("Token mal formado!");
		}
	}
	
	public static String gerarToken(String usuario, String perfil) throws Exception {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("typ", "JWT");
		HashMap<String, String> claims = new HashMap<String, String>();
		claims.put("iss", "SSO SISRH");
		claims.put("aud", "Publico");
		claims.put("user", usuario);
		claims.put("perfil", perfil);

		final Date dtCriacao = new Date();
		final Date dtExpiracao = new Date(dtCriacao.getTime() + 1000 * 60 * 15);
		String jwtToken = Jwts.builder()
				.setHeader(headers)
				.setIssuedAt(new Date())
				.setClaims(claims)
				.setSubject("Acesso RBAC")
				.setIssuedAt(dtCriacao)
				.setExpiration(dtExpiracao)
				.signWith(getPrivateKey())
				.compact();
		return jwtToken;
	}


	



@POST
@Path("autenticar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response autenticar(Login login) {
	try {
		if (login.getUsuario().equals("valeria") && login.getSenha().equals("123")) {
			return Response
					.ok()
					.entity(gerarToken("valeria", "ADMIN"))
					.build();
		}
		if (login.getUsuario().equals("ricardo") && login.getSenha().equals("123")) {
			return Response
					.ok()
					.entity(gerarToken("ricardo", "USER"))
					.build();
		}
		if (login.getUsuario().equals("bernardo") && login.getSenha().equals("123")) {
			return Response
					.ok()
					.entity(gerarToken("bernardo", "GESTOR"))
					.build();
		}

		return Response
				.status(Status.FORBIDDEN)
				.entity("{ \"mensagem\" : \"Usuario ou senha invalido!\" }")
				.build();

	} catch (Exception e) {
		return Response
				.status(Status.INTERNAL_SERVER_ERROR)
				.entity(
				"{ \"mensagem\" : \"Falha para gerar token JWT!\" , \"detalhe\" :  \"" + e.getMessage() + "\"  }")
				.build();
	}
  }

@POST
@Path("validar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response validarToken(TokenRecursoAcao tokenRecurso) {
	try {
		Jws<Claims> declaracores = validarToken(tokenRecurso.getToken());
		String perfil = declaracores.getBody().get("perfil").toString();
		Map<String, String> perfilRBAC = rbac.get(perfil);
		if (perfilRBAC != null && 
				perfilRBAC
				.get(tokenRecurso
						.getRecurso())
				.contains(tokenRecurso.getAcao())) {
			return Response
					.status(Status.OK)
					.entity("{ \"mensagem\" : \"Acesso autorizado!\" }")
					.build();
		}
	} catch (Exception e) {
		return Response
				.status(Status.FORBIDDEN)
				.entity("{ \"mensagem\" : \"Acesso negado!\" }")
				.build();
	}
	return Response
			.status(Status.FORBIDDEN)
			.entity("{ \"mensagem\" : \"Acesso negado!\" }")
			.build();
}

}


