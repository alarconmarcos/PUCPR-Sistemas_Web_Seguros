package sisrh.soap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public class ServicoBasico {
		@WebMethod(action = "ping")
		public String ping() {
			UUID uuid = UUID.randomUUID();
			return "pong: " + uuid;
		}


@WebMethod(action = "dataHoraServidor")
public String dataHoraServidor() {
		String pattern = "dd/MM/YYYY - HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());
	}
}