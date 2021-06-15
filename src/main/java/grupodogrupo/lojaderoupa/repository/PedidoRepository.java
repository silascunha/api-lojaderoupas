package grupodogrupo.lojaderoupa.repository;

import grupodogrupo.lojaderoupa.model.Pedido;
import grupodogrupo.lojaderoupa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
