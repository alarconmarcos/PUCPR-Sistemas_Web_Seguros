package sisrh.seguranca;

import java.util.*;
import io.jsonwebtoken.*;

public class RBAC {

	private static Map<String, Map<String, String>> rbac = new HashMap<>();
	static {
		inicializarRegrasRBAC();
	}

	private static void inicializarRegrasRBAC() {
		Map<String, String> perfil_servico = new HashMap<>();		
		perfil_servico.put("empregado", "GET,POST,PUT,DELETE");
		perfil_servico.put("usuario", "GET,POST,PUT,DELETE");
		perfil_servico.put("sistema", "GET");			
		
		Map<String, String> perfil_administrador = new HashMap<>();		
		perfil_administrador.put("empregado", "GET,POST,PUT,DELETE");
		perfil_administrador.put("usuario", "GET,POST,PUT,DELETE");
		perfil_administrador.put("sistema", "GET");					
		
		Map<String, String> perfil_usuario = new HashMap<>();		
		perfil_usuario.put("empregado", "GET");
		perfil_usuario.put("sistema", "GET");				
		
		rbac.put("SERVICO", perfil_servico);		
		rbac.put("ADMINISTRADOR", perfil_administrador);
		rbac.put("USUARIO", perfil_usuario);		
	}
	public static boolean autorizarAcesso(String token, String recurso, String operacao) {
		try {
			Jws<Claims> declaracores = LoginUnico.validarToken(token);
			String perfil = declaracores.getBody().get("perfil").toString();			
			Map<String, String> perfilRBAC = rbac.get(perfil);
			if(perfilRBAC != null && perfilRBAC.get(recurso).contains(operacao)){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}


}
