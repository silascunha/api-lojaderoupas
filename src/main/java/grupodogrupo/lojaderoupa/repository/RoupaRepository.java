package grupodogrupo.lojaderoupa.repository;

import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.model.enums.GeneroRoupa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoupaRepository extends JpaRepository<Roupa, Long> {

    @Query(value = "select * from Roupa WHERE ativo = true ORDER BY data_cadastro DESC LIMIT ?1", nativeQuery = true)
    List<Roupa> encontreRecentes(int limite);

    Page<Roupa> findByAtivo(Pageable pageable, Boolean ativo);

    @Query("FROM Roupa r WHERE UPPER(r.descricao) LIKE UPPER(concat('%', :descricao, '%')) AND " +
            "(:ativo is null or r.ativo = :ativo) ORDER BY r.descricao ASC")
    List<Roupa> findByDescricao(@Param("descricao") String descricao, @Param("ativo") Boolean ativo);

    Optional<Roupa> findByModelosId(Long id);

    @Query("SELECT DISTINCT r FROM Roupa r " +
            "JOIN r.modelos rm JOIN rm.tamanhosModelo rmtm " +
            "WHERE (((:categoriasId) is null OR r.categoria.id IN :categoriasId) AND " +
            "((:coresId) is null OR rm.cor.id IN :coresId) AND " +
            "(:genero is null OR r.genero = :genero) AND " +
            "((:tamanhos) is null OR rmtm.tamanho IN :tamanhos)) AND " +
            "(:descricao is null OR UPPER(r.descricao) LIKE UPPER(concat('%', :descricao, '%'))) AND " +
            "(:ativo is null or r.ativo = :ativo) " +
            "ORDER BY r.descricao")
    List<Roupa> buscarFiltrado(@Param("descricao") String descricao,
                               @Param("coresId") List<Long> coresId,
                               @Param("categoriasId") List<Long> categoriasId,
                               @Param("genero") GeneroRoupa genero,
                               @Param("tamanhos") List<String> tamanhos,
                               @Param("ativo") Boolean ativo);

}
