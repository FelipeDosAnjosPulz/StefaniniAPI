package stefanini.transactionReceiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import stefanini.dto.request.RequestRegistroPautaDTO;
import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.dto.scheduled.PautaScheduledDTO;
import stefanini.service.IPautaService;

@Component
public class PautaTransactionReceiver {

    @Autowired
    private IPautaService IPautaService;

    @JmsListener(destination = "registrarPauta", containerFactory = "myFactory")
    public void registrarPauta(RequestRegistroPautaDTO requestRegistroPautaDTO) {
        IPautaService.registrarPauta(requestRegistroPautaDTO);
    }

    @JmsListener(destination = "outRegistrarPauta", containerFactory = "myFactory")
    public void outRegistrarPauta(String response) {
        System.out.println(response);
    }

    @JmsListener(destination = "outTerminoPauta", containerFactory = "myFactory")
    public void outTerminoPauta(PautaScheduledDTO pautaScheduledDTO) {
        System.out.println("-------------------------------------------------------");
        System.out.println("-- Pauta " + pautaScheduledDTO.getNomePauta() + " foi encerrada.");
        System.out.println("-- Descricao Pauta: " + pautaScheduledDTO.getDescricaoPauta() + ".");
        System.out.println("-- Data de inicio da pauta: " + pautaScheduledDTO.getDataInicioPauta() + ".");
        System.out.println("-- Data de encerramento da pauta: " + pautaScheduledDTO.getDataFimPauta() + ".");
        System.out.println("-- Total de votos: " + pautaScheduledDTO.getVotos().size() + ".");
        System.out.println("-- Total de sim: " + pautaScheduledDTO.getVotos().stream().filter(voto -> voto.getVoto() == RequestRegistroVotoDTO.OpcoesVoto.SIM).count() + ".");
        System.out.println("-- Total de não: " + pautaScheduledDTO.getVotos().stream().filter(voto -> voto.getVoto() == RequestRegistroVotoDTO.OpcoesVoto.NAO).count() + ".");
        System.out.println("-------------------------------------------------------");
    }
}