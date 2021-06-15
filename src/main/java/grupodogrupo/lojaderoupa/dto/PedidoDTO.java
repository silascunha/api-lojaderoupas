package grupodogrupo.lojaderoupa.dto;

import grupodogrupo.lojaderoupa.model.ItemPedido;
import grupodogrupo.lojaderoupa.model.Pedido;

import java.io.Serializable;
import java.util.List;

public class PedidoDTO implements Serializable {

    private Pedido pedido;

    private List<ItemPedido> itens;

    public PedidoDTO() {
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
