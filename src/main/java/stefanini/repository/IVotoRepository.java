package stefanini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stefanini.entidade.Voto;

@Repository
public interface IVotoRepository extends JpaRepository<Voto, Long> {

    long countByCpfAndIdPauta(String cpf, int idPauta);

}
