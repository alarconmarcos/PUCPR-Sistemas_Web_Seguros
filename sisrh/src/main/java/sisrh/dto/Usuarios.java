package sisrh.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuarios {
	
	@XmlElement(name = "usuario")
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	public List<Usuario> getUsuarios() {
			return usuarios;
	}
}