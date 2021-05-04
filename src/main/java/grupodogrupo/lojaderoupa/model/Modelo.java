package grupodogrupo.lojaderoupa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Modelo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Tamanho tamanho;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cor_id")
    private Cor cor;

    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Roupa roupa;

    public Modelo() {
    }

    public Modelo(Tamanho tamanho, Cor cor, Integer quantidade) {
        this.tamanho = tamanho;
        this.cor = cor;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
        cor.getModelos().add(this);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Roupa getRoupa() {
        return roupa;
    }

    public void setRoupa(Roupa roupa) {
        this.roupa = roupa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modelo modelo = (Modelo) o;
        return Objects.equals(tamanho, modelo.tamanho) && Objects.equals(cor, modelo.cor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tamanho, cor);
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "id=" + id +
                ", tamanho='" + tamanho + '\'' +
                ", cor='" + cor + '\'' +
                ", quantidade=" + quantidade +
                ", roupa=" + roupa +
                '}';
    }
}
