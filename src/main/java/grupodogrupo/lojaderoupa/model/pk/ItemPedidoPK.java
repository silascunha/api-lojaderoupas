package grupodogrupo.lojaderoupa.model.pk;

import grupodogrupo.lojaderoupa.model.Modelo;
import grupodogrupo.lojaderoupa.model.Pedido;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemPedidoPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoPK that = (ItemPedidoPK) o;
        return Objects.equals(pedido, that.pedido) && Objects.equals(modelo, that.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, modelo);
    }
}
