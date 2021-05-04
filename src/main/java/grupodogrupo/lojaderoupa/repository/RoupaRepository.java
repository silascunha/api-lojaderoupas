package grupodogrupo.lojaderoupa.repository;

import grupodogrupo.lojaderoupa.model.Roupa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoupaRepository extends JpaRepository<Roupa, Long> {

    @Query(value = "select * from Roupa ORDER BY data_cadastro DESC LIMIT ?1", nativeQuery = true)
    List<Roupa> encontreRecentes(int limite);

}
