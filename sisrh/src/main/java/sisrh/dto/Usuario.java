package sisrh.dto;

public class Usuario {
	
	
	private String nome;

	private Integer perfil;

	private String matricula;
	
	private String senha;
	
	public Usuario() {
		
	}
	
	public Usuario(String nome, Integer perfil, String matricula, String senha) {
		super();
		this.nome = nome;
		this.perfil = perfil;
		this.matricula = matricula;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public Integer getPerfil() {
		return perfil;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getSenha() {
		return senha;
	}	
}
