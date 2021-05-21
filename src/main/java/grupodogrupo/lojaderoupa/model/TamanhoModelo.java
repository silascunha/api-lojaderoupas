package grupodogrupo.lojaderoupa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class TamanhoModelo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tamanho;
    private int quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Modelo modelo;

    public TamanhoModelo() {
    }

    public TamanhoModelo(Long id) {
        this.id = id;
    }

    public TamanhoModelo(Long id, String tamanho, int quantidade) {
        this.id = id;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
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
        TamanhoModelo that = (TamanhoModelo) o;
        return Objects.equals(tamanho, that.tamanho) && Objects.equals(modelo, that.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tamanho, modelo);
    }
}
