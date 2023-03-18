package sisrh.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Solicitacao {

	private Integer id;

	private Date data;

	private String descricao;

	private Integer situacao;

	private String matricula;
	
	public Solicitacao() {
		
	}
	

	public Solicitacao(Integer id, Date data, String descricao, Integer situacao, String matricula) {
		super();
		this.id = id;
		this.data = data;
		this.descricao = descricao;
		this.situacao = situacao;
		this.matricula = matricula;
	}


	public Integer getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public String getMatricula() {
		return matricula;
	}

}
