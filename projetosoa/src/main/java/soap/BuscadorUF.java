package soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import bean.ListaUF;
import bean.UF;

@WebService
@SOAPBinding(style = Style.RPC)

public class BuscadorUF {
        String[][] listaUFs =
                {
                        { "Rio Branco", "Acre", "413418" },
                        { "Alagoas", "Maceió", "1025360" },                    
                        { "Acre", "Rio Branco", "413418" },
                        {"Alagoas" , "Maceió", "1025360" },                                    
                        {"Amazonas", "Manaus", "2219580" },
                        {"Amapá", "Macapá", "512902" },
                        {"Bahia", "Salvador", "2886698"},
                        {"Ceará", "Fortaleza", "2686612"},
                        {"Distrito Federal", "Brasília", "3055149"},
                        {"Espírito Santo", "Vitória", "365855"},
                        {"Goiás", "Goiânia", "1536097"},
                        {"Maranhão", "São Luís", "1108975"},
                        {"Minas Gerais", "Belo Horizonte", "2521564"},
                        {"Mato Grosso", "Cuiabá", "618124"},
                        {"Mato Grosso do Sul", "Campo Grande", "906092"},
                        {"Pará", "Belém", "1499641"},
                        {"Paraíba", "João Pessoa", "817511"},
                        {"Paraná", "Curitiba", "1948626"},
                        {"Pernambuco", "Recife", "1653461"},
                        {"Piauí", "Teresina", "868075"},
                        {"Rio de Janeiro", "Rio de Janeiro", "6747815"},
                        {"Rio Grande do Norte", "Natal", "890480"},
                        {"Rio Grande do Sul", "Porto Alegre", "1488252"},
                        {"Rondônia", "Porto Velho", "539354"},
                        {"Roraima", "Boa Vista", "419652"},
                        {"Santa Catarina", "Florianópolis", "508826"},
                        {"São Paulo", "São Paulo", "12325232"},
                        {"Sergipe", "Aracaju", "664908"},
                        {"Tocantins", "Palmas", "306296"},
                };

        @WebMethod(action = "listarUFs")
        public ListaUF listarUFs() {
                ListaUF ufs = new ListaUF();
                for (String[] uf : listaUFs) {
                        ufs.getUFs().add(new UF(uf[0], uf[1], uf[2]));
                }
                return ufs;
        }
        
        @WebMethod(action = "buscarUFs")
        public ListaUF buscarUFs(@WebParam(name = "busca") String busca)  throws Exception {           
                ListaUF ufs = new ListaUF();
                for (String uf[] : listaUFs) {
                        if (uf[0].toLowerCase().contains(busca.toLowerCase())) {
                                ufs.getUFs().add(new UF(uf[0], uf[1], uf[2]));
                        }else{
                        if (uf[1].toLowerCase().contains(busca.toLowerCase())) {
                            ufs.getUFs().add(new UF(uf[0], uf[1], uf[2]));
                        }else{
                        if (Integer.parseInt(uf[2]) < (Integer.parseInt(busca))) {
                            ufs.getUFs().add(new UF(uf[0], uf[1], uf[2]));
                      }
                    }
                  }              
                }
                return ufs;
        }
}