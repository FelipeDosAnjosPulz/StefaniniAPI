package stefanini.service;

import stefanini.dto.request.RequestRegistroPautaDTO;
import stefanini.dto.response.ResponseBuscarPautaDTO;
import stefanini.entidade.Pauta;

import javax.transaction.Transactional;
import java.util.List;

public interface IPautaService {

    Pauta registrarPauta(RequestRegistroPautaDTO requestRegistroPautaDTO);

    List<ResponseBuscarPautaDTO> buscarPautas(String nomePauta);

    void excluirPautas();

}
