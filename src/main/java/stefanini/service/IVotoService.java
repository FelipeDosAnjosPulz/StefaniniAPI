package stefanini.service;

import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.entidade.Voto;

public interface IVotoService {

    Voto registrarVoto(RequestRegistroVotoDTO requestRegistroVotoDTO);

    void excluirVotos();

}
