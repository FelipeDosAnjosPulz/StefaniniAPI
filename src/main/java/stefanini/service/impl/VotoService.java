package stefanini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.entidade.Pauta;
import stefanini.entidade.Voto;
import stefanini.exception.ApplicationBusinessExceptionCode;
import stefanini.gateway.VerificarCpf;
import stefanini.repository.IPautaRepository;
import stefanini.repository.IVotoRepository;
import stefanini.service.IVotoService;
import stefanini.util.DataUtil;

import java.time.temporal.ChronoUnit;

@Service
@Transactional(readOnly = true)
public class VotoService implements IVotoService {

    @Autowired
    private IPautaRepository IPautaRepository;

    @Autowired
    private IVotoRepository IVotoRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    @Transactional()
    public Voto registrarVoto(RequestRegistroVotoDTO requestRegistroVotoDTO) {
        try {

            //valida voto
            Assert.notNull(requestRegistroVotoDTO.getVoto(), ApplicationBusinessExceptionCode.ERRO_VOTO.getErrorCode());

            //valida cpf
            Assert.notNull(requestRegistroVotoDTO.getCpf(), ApplicationBusinessExceptionCode.ERRO_CPF.getErrorCode());
            Assert.isTrue(requestRegistroVotoDTO.getCpf().length() == 11, ApplicationBusinessExceptionCode.ERRO_CPF_LENGHT.getErrorCode());

            //valida nome pauta
            Assert.notNull(requestRegistroVotoDTO.getNomePauta(), ApplicationBusinessExceptionCode.ERRO_NOME_PAUTA.getErrorCode());

            //verifica se pauta existe no banco de dados
            long quantidadeRegistro = this.IPautaRepository.countByNomePauta(requestRegistroVotoDTO.getNomePauta());
            Assert.isTrue(quantidadeRegistro == 1, ApplicationBusinessExceptionCode.ERRO_QUANTIDADE_REGISTRO_PAUTA.getErrorCode());

            // verifica se o cpf ja nao votou nesta pauta
            Pauta pauta = this.IPautaRepository.findPautaByNomePauta(requestRegistroVotoDTO.getNomePauta());
            long quantidadeVotos = this.IVotoRepository.countByCpfAndIdPauta(requestRegistroVotoDTO.getCpf(), pauta.getId());
            Assert.isTrue(quantidadeVotos == 0, ApplicationBusinessExceptionCode.ERRO_CPF_JA_VOTOU_PAUTA.getErrorCode());

            // verifica se a pauta ja expirou
            long segundos = ChronoUnit.SECONDS.between(DataUtil.dataAtual(), pauta.getDataFimPauta());
            Assert.isTrue(segundos > 0, ApplicationBusinessExceptionCode.ERRO_PAUTA_EXPIRADA.getErrorCode());

            // verifica se e um cpf valido
            Assert.isTrue(VerificarCpf.getInstance().verificarCpfValido(requestRegistroVotoDTO.getCpf()), ApplicationBusinessExceptionCode.ERRO_CPF_NAO_PODE_VOTAR.getErrorCode());

            // registra voto
            Voto voto = new Voto(requestRegistroVotoDTO.getCpf(), requestRegistroVotoDTO.getVoto(), pauta.getId());
            voto = this.IVotoRepository.save(voto);

            // envia respostasta para alguem consumir a requisicao pois nao se devolve no rest do cliente
            // essa requisicao é assincrona
            this.jmsTemplate.convertAndSend("outRegistrarVoto", voto.getId() != 0 ? "Voto registrado com sucesso." : "Erro ao registrar voto.");
            return voto;

        } catch (Exception ex) {
            // envia respostasta para alguem consumir a requisicao pois nao se devolve no rest do cliente
            // essa requisicao é assincrona
            this.jmsTemplate.convertAndSend("outRegistrarVoto", ex.getMessage());
            return new Voto();
        }
    }

    @Override
    @Transactional()
    public void excluirVotos() {
        this.IVotoRepository.deleteAll();
    }

}
