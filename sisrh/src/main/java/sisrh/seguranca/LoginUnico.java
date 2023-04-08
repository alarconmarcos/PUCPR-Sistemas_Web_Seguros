package sisrh.seguranca;

import java.math.*;
import java.security.*;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Date;
import java.util.*;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.security.*;
import sisrh.banco.*;
import sisrh.dto.*;

public class LoginUnico {	

	private static Key chavePrivada = null;

	private static Key getPrivateKey() {
		if (chavePrivada == null) {
			String privateKey = "b8338e24f11f4692a95738fe2e893c2ab8338e24f11f46";
			byte[] keyBytes = Decoders.BASE64.decode(privateKey);
			chavePrivada = Keys.hmacShaKeyFor(keyBytes);
		}
		return chavePrivada;
	}
	public static String md5(String valor) throws Exception {
		String s = valor;
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(s.getBytes(), 0, s.length());
		return "" + new BigInteger(1, m.digest()).toString(16);
	}
	public static Usuario buscarUsuario(String usuario, String senha) throws Exception {
		try {
			String senhaMd5 = md5(senha);
			Connection conn = Banco.getConexao();
			String sql = "SELECT nome, senha, perfil, matricula "
					+ "FROM Usuario "
					+ "WHERE nome = ? and senha = ?";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setString(1, usuario);
			prepStmt.setString(2, senhaMd5);

			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				Usuario us = new Usuario(rs.getString("nome"), rs.getInt("perfil"),
						rs.getString("matricula"), null);
				return us;

			}
		} catch (Exception e) {
			System.out.println("Carga de dados.......................[NOK]");
			e.printStackTrace();
		}
		return null;
	}
	public static String geraToken(String usuario, String senha) throws Exception {
		Usuario user = buscarUsuario(usuario, senha);
		if (user != null) {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("typ", "JWT");

			// Construcao do token
			HashMap<String, String> claims = new HashMap<String, String>();
			claims.put("iss", "SSO SISRH");
			claims.put("aud", "Publico");
			claims.put("user", user.getNome());
			switch (user.getPerfil()) {
			case 0:
				claims.put("perfil", "USUARIO");
				break;
			case 1:
				claims.put("perfil", "SERVICO");
				break;
			case 2:
				claims.put("perfil", "ADMINISTRADOR");
				break;
			}
			final Date dtCriacao = new Date();
			final Date dtExpiracao = new Date(dtCriacao.getTime() + 1000 * 60 * 15);
			String jwtToken = Jwts.builder().setHeader(headers)
					.setIssuedAt(new Date()).setClaims(claims)
					.setSubject("Acesso SISRH")
					.setIssuedAt(dtCriacao)
					.setExpiration(dtExpiracao)
					.signWith(LoginUnico.getPrivateKey())
					.compact();
			return jwtToken;
		}
		return null;
	}
	public static Jws<Claims> validarToken(String tokenJWT) throws Exception {		
		try {
			Jws<Claims> credencial = Jwts
					.parserBuilder()
					.setSigningKey(getPrivateKey())
					.build()
					.parseClaimsJws(tokenJWT);
			return credencial;
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("Token expirado!");
		} catch (MalformedJwtException ex) {
			throw new RuntimeException("Token mal formado!");
		}	
	}

}

