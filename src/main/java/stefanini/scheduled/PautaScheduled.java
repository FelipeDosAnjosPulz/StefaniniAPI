package stefanini.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stefanini.dto.scheduled.PautaScheduledDTO;
import stefanini.entidade.Pauta;
import stefanini.repository.IPautaRepository;
import stefanini.util.DataUtil;

import java.util.List;

@Component
@EnableScheduling
public class PautaScheduled {

    @Autowired
    private IPautaRepository IPautaRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    private final long SEGUNDO = 1000;

    @Scheduled(fixedRate = SEGUNDO * 20)
    public void verificaPorSegundo() {

        List<Pauta> list = this.IPautaRepository.findAllByDataFimPautaBeforeAndSituacaoEquals(DataUtil.dataAtual(), Pauta.Situacao.ABERTA);
        list.stream().forEach(pauta -> {
            pauta.setSituacao(Pauta.Situacao.ENCERRADA);
            this.IPautaRepository.save(pauta);
            this.jmsTemplate.convertAndSend("outTerminoPauta",
                    new PautaScheduledDTO(
                            pauta.getId(),
                            pauta.getVotos(),
                            pauta.getNomePauta(),
                            pauta.getDescricaoPauta(),
                            pauta.getSituacao(),
                            DataUtil.LocalDateTimeToString(pauta.getDataInicioPauta()),
                            DataUtil.LocalDateTimeToString(pauta.getDataFimPauta())));
        });
    }

}
