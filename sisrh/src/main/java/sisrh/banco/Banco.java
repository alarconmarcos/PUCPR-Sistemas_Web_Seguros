package sisrh.banco;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import sisrh.dto.Empregado;
import sisrh.dto.Solicitacao;
import sisrh.dto.Usuario;

public class Banco {

	private static Connection conn;

	static {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			System.out.println("Carregar driver HSQLDB...............[OK]");
		} catch (Exception e) {
			System.err.println("Carregar driver HSQLDB...............[NOK]");
			e.printStackTrace();					
		}
		criarConexaoBanco();
	}

	static private void criarConexaoBanco() {
		try {
			conn = DriverManager.getConnection("jdbc:hsqldb:file:C:\\workspace\\sisrh_db\\rh_db", "SA", "");
			System.out.println("Conexão ao banco BANCO_SISRH.........[OK]");
		} catch (SQLException e) {
			System.out.println("Conexão ao banco BANCO_SISRH.........[NOK]");
			if (e.getMessage().contains("lockFile")) {
				JOptionPane.showMessageDialog(null,
						"O banco está bloqueado \n porque o Tomcat não liberou a conexão. REINICIE O TOMCAT");

			} else {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConexao() {
		return conn;
	}

	public static void executarScript(String script) throws SQLException {
		Connection conn = Banco.getConexao();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(script);
		stmt.close();
	}

	// ---------------------- LISTAGENS ----------------------

	public static List<Empregado> listarEmpregados() throws Exception {
		List<Empregado> lista = new ArrayList<Empregado>();
		Connection conn = Banco.getConexao();
		String sql = "SELECT * FROM Empregado";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			String matricula = rs.getString("matricula");
			String nome = rs.getString("nome");
			Date admissao = rs.getDate("admissao");
			Date desligamento = rs.getDate("desligamento");
			Double salario = rs.getDouble("salario");
			Empregado emp = new Empregado(matricula, nome, admissao, desligamento, salario);
			lista.add(emp);
		}
		rs.close();
		prepStmt.close();
		return lista;
	}

	public static List<Usuario> listarUsuarios() throws Exception {
		List<Usuario> lista = new ArrayList<Usuario>();
		Connection conn = Banco.getConexao();
		String sql = "SELECT * FROM Usuario";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			String nome = rs.getString("nome");
			Integer perfil = rs.getInt("perfil");
			String matricula = rs.getString("matricula");
			String senha = rs.getString("senha");
			Usuario emp = new Usuario(nome, perfil, matricula, senha);
			lista.add(emp);
		}
		rs.close();
		prepStmt.close();
		return lista;
	}

	public static List<Solicitacao> listarSolicitacoes() throws Exception {
		List<Solicitacao> lista = new ArrayList<Solicitacao>();
		Connection conn = Banco.getConexao();
		String sql = "SELECT * FROM Solicitacao";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			Integer id = rs.getInt("id");
			Date data = rs.getDate("data");
			String descricao = rs.getString("descricao");
			Integer situacao = rs.getInt("situacao");
			String matricula = rs.getString("matricula");
			Solicitacao solicitacao = new Solicitacao(id, data, descricao, situacao, matricula);
			lista.add(solicitacao);
		}
		rs.close();
		prepStmt.close();
		return lista;
	}
	
	// ---------------------- CONSULTAS ----------------------
	
	public static Empregado buscarEmpregadoPorMatricula(String matricula) throws SQLException {
		Empregado emp = null;
		Connection conn = Banco.getConexao();
		String sql = "SELECT * FROM Empregado WHERE matricula = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, matricula);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			String _matricula = rs.getString("matricula");
			String nome = rs.getString("nome");
			Date admissao = rs.getDate("admissao");
			Date desligamento = rs.getDate("desligamento");
			Double salario = rs.getDouble("salario");
			emp = new Empregado(_matricula, nome, admissao, desligamento, salario);
		}
		rs.close();
		prepStmt.close();
		return emp;
	}
	
	public static Usuario buscarUsuarioPorMatricula(String matricula) throws SQLException {
		Usuario usu = null;
		Connection conn = Banco.getConexao();
		String sql = "SELECT * FROM Usuario WHERE matricula = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, matricula);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			String nome = rs.getString("nome");
			Integer perfil = rs.getInt("perfil");
			String _matricula = rs.getString("matricula");
			String senha = rs.getString("senha");			
			usu = new Usuario(nome, perfil, _matricula, senha);
		}
		rs.close();
		prepStmt.close();
		return usu;
	}
	
	public static Solicitacao buscarSolicitacaoPorId(Integer _id) throws SQLException {
		Solicitacao solicitacao = null;
		Connection conn = Banco.getConexao();
		String sql = "SELECT * FROM Solicitacao WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, _id);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			Integer id = rs.getInt("id");
			Date data = rs.getDate("data");
			String descricao = rs.getString("descricao");
			Integer situacao = rs.getInt("situacao");
			String matricula = rs.getString("matricula");
			solicitacao = new Solicitacao(id, data, descricao, situacao, matricula);
			
		}
		rs.close();
		prepStmt.close();
		return solicitacao;
	}
	
	
	public static int idSolicitacao() throws SQLException {
		Integer id = null;		
		Connection conn = Banco.getConexao();
		String sql = "SELECT MAX(ID) as id FROM Solicitacao";
		PreparedStatement prepStmt = conn.prepareStatement(sql);		
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			id = rs.getInt("id") + 1;
		}
		rs.close();
		prepStmt.close();
		return id;
	}

	
	// ---------------------- INCLUSOES ----------------------	
	
	public static Empregado incluirEmpregado(Empregado empregado) throws SQLException {		
		if (empregado == null) return null;
		Connection conn = Banco.getConexao();
		String sql = "INSERT into Empregado "
				+ " (matricula, nome, admissao, desligamento, salario) "
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);		
		prepStmt.setString (1, empregado.getMatricula());
		prepStmt.setString (2, empregado.getNome());
		prepStmt.setDate(3, empregado.getAdmissao() != null ? new java.sql.Date(empregado.getAdmissao().getTime()) : null );
		prepStmt.setDate(4, empregado.getDesligamento() != null ? new java.sql.Date(empregado.getDesligamento().getTime()) : null );
		prepStmt.setDouble(5, empregado.getSalario());		
		prepStmt.executeUpdate();
		prepStmt.close();	
		return buscarEmpregadoPorMatricula(empregado.getMatricula());		
	}
	
	public static Usuario incluirUsuario(Usuario usuario) throws Exception {		
		if (usuario == null) return null;
		Connection conn = Banco.getConexao();
		String sql = "INSERT into Usuario "
				+ " (nome, perfil, senha, matricula) "
				+ " values (?, ?, ?, ?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);		
		prepStmt.setString (1, usuario.getNome());
		prepStmt.setInt(2, usuario.getPerfil());
		prepStmt.setString(3, md5(usuario.getSenha()));
		prepStmt.setString(4, usuario.getMatricula());				
		prepStmt.executeUpdate();
		prepStmt.close();	
		return buscarUsuarioPorMatricula(usuario.getMatricula());
	}

	public static Solicitacao incluirSolicitacao(Solicitacao solicitacao) throws SQLException {
		if (solicitacao == null) return null;
		Integer id = idSolicitacao();
		Connection conn = Banco.getConexao();
		String sql = "INSERT into Solicitacao "
				+ " (id, data, descricao, situacao, matricula) "
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);		
		prepStmt.setInt(1, id);
		prepStmt.setDate(2, solicitacao.getData() != null ? new java.sql.Date(solicitacao.getData().getTime()) : null );
		prepStmt.setString (3, solicitacao.getDescricao());
		prepStmt.setInt(4, solicitacao.getSituacao() );
		prepStmt.setString(5, solicitacao.getMatricula());				
		prepStmt.executeUpdate();
		prepStmt.close();	
		return buscarSolicitacaoPorId(id);	
	}
	
	
	// ---------------------- ALTERACOES ----------------------
	
	public static Empregado alterarEmpregado(String matricula, Empregado empregado) throws SQLException {
		if (empregado == null || matricula == null) return null;
		Connection conn = Banco.getConexao();
		String sql = "UPDATE Empregado SET "
				+ "nome = ?, admissao = ?, desligamento = ?, salario = ? "
				+ "WHERE matricula = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.setString (1, empregado.getNome());
		prepStmt.setDate(2, empregado.getAdmissao() != null ? new java.sql.Date(empregado.getAdmissao().getTime()) : null );
		prepStmt.setDate(3, empregado.getDesligamento() != null ? new java.sql.Date(empregado.getDesligamento().getTime()) : null );
		prepStmt.setDouble(4, empregado.getSalario());		
		prepStmt.setString(5, matricula);
		
		prepStmt.executeUpdate();
		prepStmt.close();	
		return buscarEmpregadoPorMatricula(matricula);
	}
	

	public static Usuario alterarUsuario(String matricula, Usuario usuario) throws Exception {
		if (usuario == null || matricula == null) return null;
		Connection conn = Banco.getConexao();
		String sql = "UPDATE Usuario SET "
				+ "nome = ?, perfil = ?, senha = ?, matricula = ? "
				+ "WHERE matricula = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.setString (1, usuario.getNome());
		prepStmt.setInt(2, usuario.getPerfil());
		prepStmt.setString(3, md5(usuario.getSenha()));				
		prepStmt.setString(4, matricula);
		prepStmt.setString(5, matricula);
		
		prepStmt.executeUpdate();
		prepStmt.close();	
		return buscarUsuarioPorMatricula(matricula);
	}
	
	public static Solicitacao alterarSolicitacao(Integer id, Solicitacao solicitacao) throws SQLException {
		if (solicitacao == null || id == null) return null;
		Connection conn = Banco.getConexao();
		String sql = "UPDATE Solicitacao SET "
				+ "data = ?, descricao = ?, situacao = ?, matricula = ? "
				+ "WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.setDate(1, solicitacao.getData() != null ? new java.sql.Date(solicitacao.getData().getTime()) : null );		
		prepStmt.setString (2, solicitacao.getDescricao());
		prepStmt.setInt (3, solicitacao.getSituacao());
		prepStmt.setString (4, solicitacao.getMatricula());
		prepStmt.setInt(5, id);		
		prepStmt.executeUpdate();
		prepStmt.close();	
		return buscarSolicitacaoPorId(id);
	}
	
	// ---------------------- EXCLUSOES ----------------------
	
	public static boolean excluirEmpregado(String matricula) throws SQLException {	
		Connection conn = Banco.getConexao();
		String sql = "DELETE FROM Empregado " 
				+ "WHERE matricula = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, matricula);
		prepStmt.executeUpdate();
		prepStmt.close();
		return true;
	}
	
	public static boolean excluirSolicitacao(Integer id) throws SQLException {
		Connection conn = Banco.getConexao();
		String sql = "DELETE FROM Solicitacao " 
				+ "WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);
		prepStmt.executeUpdate();
		prepStmt.close();
		return true;
		
	}
	
	public static boolean excluirUsuario(String matricula) throws SQLException {	
		Connection conn = Banco.getConexao();
		String sql = "DELETE FROM Usuario " 
				+ "WHERE matricula = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, matricula);
		prepStmt.executeUpdate();
		prepStmt.close();
		return true;
	}
	
	// ---------------------- AUXILIAR ----------------------

	public static String md5(String valor) throws Exception {
		String s = valor;
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(s.getBytes(), 0, s.length());
		return "" + new BigInteger(1, m.digest()).toString(16);
	}

}