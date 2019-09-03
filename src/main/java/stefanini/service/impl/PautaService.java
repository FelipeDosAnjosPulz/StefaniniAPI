package stefanini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import stefanini.dto.request.RequestRegistroPautaDTO;
import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.dto.response.ResponseBuscarPautaDTO;
import stefanini.entidade.Pauta;
import stefanini.exception.ApplicationBusinessExceptionCode;
import stefanini.repository.IPautaRepository;
import stefanini.service.IPautaService;
import stefanini.util.DataUtil;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PautaService implements IPautaService {

    @Autowired
    private IPautaRepository IPautaRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    @Transactional()
    public Pauta registrarPauta(RequestRegistroPautaDTO requestRegistroPautaDTO) {

        try {

            //valida nome pauta
            Assert.notNull(requestRegistroPautaDTO.getNomePauta(), ApplicationBusinessExceptionCode.ERRO_NOME_PAUTA.getErrorCode());
            Assert.isTrue(requestRegistroPautaDTO.getNomePauta().length() < 100, ApplicationBusinessExceptionCode.ERRO_NOME_PAUTA_LENGHT.getErrorCode());

            //valida descricao
            if (requestRegistroPautaDTO.getDescricaoPauta() != null) {
                Assert.isTrue(requestRegistroPautaDTO.getDescricaoPauta().length() < 1000, ApplicationBusinessExceptionCode.ERRO_DESCRICAO_PAUTA_LENGHT.getErrorCode());
            }

            //valida se pauta ja foi cadastrada
            long quantidadePauta = this.IPautaRepository.countByNomePauta(requestRegistroPautaDTO.getNomePauta());
            Assert.isTrue(quantidadePauta == 0, ApplicationBusinessExceptionCode.ERRO_PAUTA_JA_REGISTRADA.getErrorCode());

            // cria uma instancia de pauta
            Pauta pauta = new Pauta(
                    requestRegistroPautaDTO.getNomePauta(),
                    requestRegistroPautaDTO.getDescricaoPauta(),
                    DataUtil.dataAtual(),
                    DataUtil.dataAtual().plusMinutes(requestRegistroPautaDTO.getExpirarPauta() > 0 ?
                            requestRegistroPautaDTO.getExpirarPauta() : 1),
                    Pauta.Situacao.ABERTA);// adiciona o tempo de expiracao

            //registra a pauta
            pauta = this.IPautaRepository.save(pauta);

            // envia respostasta para alguem consumir a requisicao pois nao se devolve no rest do cliente
            // essa requisicao é assincrona
            this.jmsTemplate.convertAndSend("outRegistrarPauta", pauta.getId() != 0 ? "Pauta (" + pauta.getNomePauta() + ") registrada com sucesso." : "Erro em registrar pauta.");
            return pauta;

        } catch (Exception ex) {
            //envia respostasta para alguem consummir a requisicao
            this.jmsTemplate.convertAndSend("outRegistrarPauta", ex.getMessage());
            return new Pauta();
        }
    }

    @Override
    public List<ResponseBuscarPautaDTO> buscarPautas(String nomePauta) {

        List<Pauta> pautaList;
        List<ResponseBuscarPautaDTO> responseBuscarPautaDTOList = new ArrayList<>();
        if (nomePauta == null) {
            pautaList = this.IPautaRepository.findAll();
        } else {
            pautaList = this.IPautaRepository.findPautaByNomePautaOrderById(nomePauta);
        }

        for (Pauta pauta : pautaList) {
            String descricaoTempo = "";
            long segundos = ChronoUnit.SECONDS.between(DataUtil.dataAtual(), pauta.getDataFimPauta());
            if (segundos < 60) {
                descricaoTempo = " segundos restantes.";
            }
            if (segundos > 60 && segundos < 120) {
                segundos = Math.round(segundos / 60) + 1;
                descricaoTempo = " minutos restante.";
            }
            if (segundos > 120) {
                segundos = Math.round(segundos / 60) + 1;
                descricaoTempo = " minutos restantes.";
            }

            ResponseBuscarPautaDTO responseBuscarPautaDTO = new ResponseBuscarPautaDTO(
                    pauta.getNomePauta(),
                    pauta.getDescricaoPauta(),
                    DataUtil.LocalDateTimeToString(pauta.getDataInicioPauta()),
                    DataUtil.LocalDateTimeToString(pauta.getDataFimPauta()),
                    segundos < 0 ? "Pauta encerrada." : segundos + descricaoTempo,
                    pauta.getVotos().size(),
                    pauta.getVotos().stream().filter(voto -> voto.getVoto() == RequestRegistroVotoDTO.OpcoesVoto.SIM).count(),
                    pauta.getVotos().stream().filter(voto -> voto.getVoto() == RequestRegistroVotoDTO.OpcoesVoto.NAO).count()
            );
            responseBuscarPautaDTOList.add(responseBuscarPautaDTO);
        }

        return responseBuscarPautaDTOList;
    }

    @Override
    @Transactional()
    public void excluirPautas() {
        this.IPautaRepository.deleteAll();
    }

}
