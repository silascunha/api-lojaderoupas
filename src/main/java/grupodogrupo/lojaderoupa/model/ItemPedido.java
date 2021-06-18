package grupodogrupo.lojaderoupa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grupodogrupo.lojaderoupa.model.pk.ItemPedidoPK;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class ItemPedido implements Serializable {

    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    @Column(nullable = false)
    private Integer quantidade;
    @Column(nullable = false)
    private Double preco;
    @Column(nullable = false)
    private String tamanho;

    public ItemPedido() {}

    public ItemPedido(Modelo modelo, Pedido pedido, Integer quantidade, Double preco, String tamanho) {
        this.quantidade = quantidade;
        this.preco = preco;
        id.setModelo(modelo);
        id.setPedido(pedido);
        this.tamanho = tamanho;
    }

    private Modelo getModelo() {
        return id.getModelo();
    }

    public void setModelo(Modelo modelo) {
        id.setModelo(modelo);
    }

    @JsonIgnore
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

    public Map<String, Object> getDadosProduto() {
        Map<String, Object> map = new HashMap<>();

        map.put("idModelo", getModelo().getId());
        map.put("idRoupa", getModelo().getRoupa().getId());
        map.put("descricao", getModelo().getRoupa().getDescricao());
        map.put("cor", getModelo().getCor());
        map.put("imagens", getModelo().getImagens());

        return map;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    @JsonIgnore
    public Long getModeloId() {
        return getModelo().getId();
    }

    @JsonIgnore
    public Roupa getRoupa() {
        return getModelo().getRoupa();
    }

    public Double getSubTotal() {
        return preco * quantidade;
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
