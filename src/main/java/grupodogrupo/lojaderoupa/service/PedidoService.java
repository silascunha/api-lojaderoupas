package grupodogrupo.lojaderoupa.service;

import grupodogrupo.lojaderoupa.dto.PedidoDTO;
import grupodogrupo.lojaderoupa.model.*;
import grupodogrupo.lojaderoupa.repository.ItemPedidoRepository;
import grupodogrupo.lojaderoupa.repository.PedidoRepository;
import grupodogrupo.lojaderoupa.repository.RoupaRepository;
import grupodogrupo.lojaderoupa.repository.TamanhoModeloRepository;
import grupodogrupo.lojaderoupa.service.exceptions.DatabaseException;
import grupodogrupo.lojaderoupa.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemRepository;

    @Autowired
    private RoupaRepository roupaRepository;

    @Autowired
    private TamanhoModeloRepository tamanhoModeloRepository;

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

        Set<TamanhoModelo> tamanhoModelos = new HashSet<>();

        for (ItemPedido ip : itens) {
            ip.setPedido(obj);

            TamanhoModelo tamanhoModelo = tamanhoModeloRepository.findByTamanhoAndModeloId(ip.getTamanho(), ip.getModeloId()).get();
            tamanhoModelo.setQuantidade(tamanhoModelo.getQuantidade() - ip.getQuantidade());
            tamanhoModelos.add(tamanhoModelo);
        }
        itens = itemRepository.saveAll(itens);
        tamanhoModeloRepository.saveAll(tamanhoModelos);

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
