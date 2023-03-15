package sisrh.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Empregados {
	
	@XmlElement(name = "empregado")
	private List<Empregado> empregados = new ArrayList<Empregado>();
	
	public List<Empregado> getEmpregados() {
			return empregados;
	}
}