package grupodogrupo.lojaderoupa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import grupodogrupo.lojaderoupa.model.enums.Genero;
import grupodogrupo.lojaderoupa.model.enums.TipoTamanho;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
public class Roupa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    private Boolean ativo = false;

    @Column(nullable = false)
    private int tipoTamanho;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @Column(nullable = false)
    private Instant dataCadastro;

    @OneToMany(mappedBy = "roupa", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<Modelo> modelos = new HashSet<>();

    public Roupa() {
    }

    public Roupa(Long id, String descricao, Instant dataCadastro, Double preco, Boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.preco = preco;
        this.ativo = ativo;
    }

    public Roupa(Long id, String descricao, Instant dataCadastro, Double preco) {
        this.id = id;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoTamanho getTipoTamanho() {
        return TipoTamanho.valueOf(this.tipoTamanho);
    }

    public void setTipoTamanho(TipoTamanho tipoTamanho) {
        if (tipoTamanho != null) {
            this.tipoTamanho = tipoTamanho.getCodigo();
        }
    }

    public Genero getGenero() {
        return this.genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantidadeTotal() {
        Integer quantidadeTotal = 0;
        for (Modelo modelo : modelos) {
            quantidadeTotal += modelo.getQuantidadeTotal();
        }

        return quantidadeTotal;
    }

    public Set<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(Set<Modelo> modelos) {
        modelos.forEach(x -> {
            List<String> listaTamanho = ListaTamanho.getTamanhos(this.getTipoTamanho());
            boolean temTamanhoInvalido = !listaTamanho.containsAll(x.getTamanhos());

            if (temTamanhoInvalido) {
                throw new IllegalArgumentException("Tamanho inválido com o tipo selecionado");
            }

            x.getTamanhosModelo().sort(Comparator.comparingInt(tm -> listaTamanho.indexOf(tm.getTamanho())));

            x.setRoupa(this);
        });
        this.modelos = modelos;
    }

    @JsonIgnore
    public List<Imagem> getImagens() {
        List<Imagem> imagens = new ArrayList<>();

        modelos.forEach(modelo -> imagens.addAll(modelo.getImagens()));

        return imagens;
    }

    public boolean addModelo(Modelo modelo) {
        boolean temTamanhoInvalido = !ListaTamanho.getTamanhos(this.getTipoTamanho())
                .containsAll(modelo.getTamanhos());

        if (temTamanhoInvalido) {
            throw new IllegalArgumentException("Tamanho inválido com o tipo selecionado");
        }

        modelo.setRoupa(this);
        return modelos.add(modelo);
    }

    public boolean removeModelo(Modelo modelo) {
        boolean removido = modelos.remove(modelo);
        modelo.setRoupa(null);
        return removido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roupa roupa = (Roupa) o;
        return Objects.equals(id, roupa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
