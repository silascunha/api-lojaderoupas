package grupodogrupo.lojaderoupa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Modelo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cor_id")
    private Cor cor;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<TamanhoModelo> tamanhosModelo = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Roupa roupa;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "modelo_id")
    private List<Imagem> imagens = new ArrayList<>();

    public Modelo() {
    }

    public Modelo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roupa getRoupa() {
        return roupa;
    }

    public void setRoupa(Roupa roupa) {
        this.roupa = roupa;
    }

    public List<TamanhoModelo> getTamanhosModelo() {
        return tamanhosModelo;
    }

    public void setTamanhosModelo(List<TamanhoModelo> tamanhosModelo) {
        tamanhosModelo.forEach(x -> {
            x.setModelo(this);
        });

        this.tamanhosModelo = tamanhosModelo;
    }

    public boolean addTamanhoModelo(TamanhoModelo obj) {
        obj.setModelo(this);
        return this.tamanhosModelo.add(obj);
    }

    public boolean removeTamanhoModelo(TamanhoModelo obj) {
        obj.setModelo(null);
        return this.tamanhosModelo.remove(obj);
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Integer getQuantidadeTotal() {
        Integer quantidadeTotal = 0;

        for (TamanhoModelo elemento : tamanhosModelo) {
            quantidadeTotal += elemento.getQuantidade();
        }

        return quantidadeTotal;
    }

    @JsonIgnore
    public List<String> getTamanhos() {
        return this.tamanhosModelo.stream()
                .map(TamanhoModelo::getTamanho)
                .collect(Collectors.toList());
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modelo modelo = (Modelo) o;
        return Objects.equals(cor, modelo.cor) && Objects.equals(roupa, modelo.roupa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cor, roupa);
    }
}