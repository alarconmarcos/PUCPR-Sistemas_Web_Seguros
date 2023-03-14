package sisrh.dto;

import java.util.Date;

public class Empregado {

	private String matricula;

	private String nome;

	private Date admissao;

	private Date desligamento;

	private Double salario;

	public Empregado() {

	}

	public Empregado(String matricula, String nome, Date admissao, Date desligamento, Double salario) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.admissao = admissao;
		this.desligamento = desligamento;
		this.salario = salario;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getNome() {
		return nome;
	}

	public Date getAdmissao() {
		return admissao;
	}

	public Date getDesligamento() {
		return desligamento;
	}

	public Double getSalario() {
		return salario;
	}
}
