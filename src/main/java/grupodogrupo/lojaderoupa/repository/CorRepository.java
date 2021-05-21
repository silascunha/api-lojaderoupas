package grupodogrupo.lojaderoupa.repository;

import grupodogrupo.lojaderoupa.model.Cor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorRepository extends JpaRepository<Cor, Long> {
}
