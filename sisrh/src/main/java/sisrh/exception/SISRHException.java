package sisrh.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "SISRH")
public class SISRHException extends Exception {

	private static final long serialVersionUID = 1L;
	public SISRHException(String mensagem) {
		super(mensagem);
	}
}