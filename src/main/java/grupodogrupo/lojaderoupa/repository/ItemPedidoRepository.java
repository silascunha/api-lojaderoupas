package grupodogrupo.lojaderoupa.repository;

import grupodogrupo.lojaderoupa.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
