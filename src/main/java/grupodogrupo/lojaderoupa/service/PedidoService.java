package grupodogrupo.lojaderoupa.service;

import grupodogrupo.lojaderoupa.dto.PedidoDTO;
import grupodogrupo.lojaderoupa.model.ItemPedido;
import grupodogrupo.lojaderoupa.model.Pedido;
import grupodogrupo.lojaderoupa.model.Usuario;
import grupodogrupo.lojaderoupa.repository.ItemPedidoRepository;
import grupodogrupo.lojaderoupa.repository.PedidoRepository;
import grupodogrupo.lojaderoupa.service.exceptions.DatabaseException;
import grupodogrupo.lojaderoupa.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemRepository;

    public List<Pedido> getAll() {
        List<Pedido> lista = pedidoRepository.findAll();

        return lista;
    }

    public Pedido getById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        return pedido.orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public Pedido insert(PedidoDTO dto, Usuario usuario) {
        Pedido obj = dto.getPedido();
        obj.setCliente(usuario);
        obj.setDataPedido(Instant.now());

        if (obj.getPagamento() != null)
            obj.getPagamento().setMomento(Instant.now());


        obj = pedidoRepository.save(obj);

        List<ItemPedido> itens = dto.getItens();

        for (ItemPedido ip : itens) {
            ip.setPedido(obj);
        }
        itemRepository.saveAll(itens);

        return obj;
    }

    public Pedido update(Long id, Pedido pedido) {
        try {
            Pedido obj = pedidoRepository.getOne(id);

            updateData(obj, pedido);

            return pedidoRepository.save(obj);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new NotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            pedidoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new NotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(Pedido objAntigo, Pedido objNovo) {
        objAntigo.setStatus(objNovo.getStatus());
        objAntigo.setPagamento(objAntigo.getPagamento());
    }
}
