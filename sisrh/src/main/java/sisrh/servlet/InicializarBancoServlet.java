package sisrh.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import sisrh.banco.Banco;
import sisrh.dto.Empregado;
import sisrh.dto.Solicitacao;
import sisrh.dto.Usuario;

/**
 * Classe para Inicialiação do Banco de Dados
 * @author pucpr
 *
 */
public class InicializarBancoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.out.println("----- SISRH Inicialização ----");
		System.out.println("Inicialização!");
		carregarEstruturaDados();
		super.init();
	}

	/**
	 * Se não existir tabelas, criar a partir do script sql
	 */
	private void carregarEstruturaDados() {
		try {						
			Banco.executarScript(carregarScript("db_sisrh_estrutura.sql"));			
			List<Empregado> empregados = Banco.listarEmpregados();			
			if (empregados.isEmpty()) {		
				Banco.executarScript(carregarScript("db_sisrh_dados.sql"));				
				System.out.println("Carga inicial de dados...............[OK]");
				empregados = Banco.listarEmpregados();				
			}			
			List<Usuario> usuarios = Banco.listarUsuarios();
			List<Solicitacao> solicitacoes = Banco.listarSolicitacoes();
			System.out.println("Testar consultas ao banco................");
			System.out.println("->\t ["+ empregados.size() + "] empregados.");
			System.out.println("->\t ["+ usuarios.size() + "] usuarios.");
			System.out.println("->\t ["+ solicitacoes.size() + "] solicitacoes.");
			System.out.println("Teste................................[OK]");			
		}catch(Exception e) {	
			System.out.println("Carga de dados.......................[NOK]");
			e.printStackTrace();			
		}
	}

	
	/**
	 * Carrega e retorna conteúdo de um arquivo .sql
	 * 
	 * @param file
	 * @return
	 * @throws URISyntaxException
	 */
	public static String carregarScript(String file) throws URISyntaxException {
		StringBuilder contentBuilder = new StringBuilder();
		URI caminho = InicializarBancoServlet.class.getResource("/" + file).toURI();
		try (Stream<String> stream = Files.lines(Paths.get(caminho), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}

}
