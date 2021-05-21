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
    private String descricao;
    private Double preco;
    private Boolean ativo = false;

    private int tipoTamanho;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToMany
    @JoinTable(
            name = "roupa_categoria", joinColumns = @JoinColumn(name = "roupa_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
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

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
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
            boolean temTamanhoInvalido = !ListaTamanho.getTamanhos(this.getTipoTamanho())
                    .containsAll(x.getTamanhos());

            if (temTamanhoInvalido) {
                throw new IllegalArgumentException("Tamanho inválido com o tipo selecionado");
            }

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
