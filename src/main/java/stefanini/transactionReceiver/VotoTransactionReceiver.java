package stefanini.transactionReceiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.service.IVotoService;

@Component
public class VotoTransactionReceiver {

    @Autowired
    private IVotoService IVotoService;

    @JmsListener(destination = "registrarVoto", containerFactory = "myFactory")
    public void registrarVoto(RequestRegistroVotoDTO requestRegistroPautaDTO) {
        IVotoService.registrarVoto(requestRegistroPautaDTO);
    }

    @JmsListener(destination = "outRegistrarVoto", containerFactory = "myFactory")
    public void outRegistrarVoto(String response) {
        System.out.println(response);
    }

}
