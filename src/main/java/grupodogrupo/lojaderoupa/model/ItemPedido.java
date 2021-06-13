package grupodogrupo.lojaderoupa.model;

import grupodogrupo.lojaderoupa.model.pk.ItemPedidoPK;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ItemPedido implements Serializable {

    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    private Double preco;

    public ItemPedido() {}

    public ItemPedido(Modelo modelo, Pedido pedido, Integer quantidade, Double preco) {
        this.quantidade = quantidade;
        this.preco = preco;
        id.setModelo(modelo);
        id.setPedido(pedido);
    }

    public Modelo getModelo() {
        return id.getModelo();
    }

    public void setModelo(Modelo modelo) {
        id.setModelo(modelo);
    }

    public Pedido getPedido() {
        return id.getPedido();
    }

    public void setPedido(Pedido pedido) {
        id.setPedido(pedido);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
