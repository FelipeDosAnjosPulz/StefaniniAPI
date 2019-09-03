package stefanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stefanini.entidade.Pauta;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPautaRepository extends JpaRepository<Pauta, Long> {

    long countByNomePauta(String nomePauta);

    Pauta findPautaByNomePauta(String nomePauta);

    List<Pauta> findPautaByNomePautaOrderById(String nomePauta);

    List<Pauta> findAllByDataFimPautaBeforeAndSituacaoEquals(LocalDateTime localDateTime, Pauta.Situacao situacao);

}
