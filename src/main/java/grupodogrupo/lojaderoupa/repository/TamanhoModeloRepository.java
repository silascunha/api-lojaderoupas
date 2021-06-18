package grupodogrupo.lojaderoupa.repository;

import grupodogrupo.lojaderoupa.model.TamanhoModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TamanhoModeloRepository extends JpaRepository<TamanhoModelo, Long> {

    Optional<TamanhoModelo> findByTamanhoAndModeloId(String tamanho, Long modeloId);
}
