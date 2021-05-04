package grupodogrupo.lojaderoupa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Roupa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double preco;
    private Boolean ativo;

    @ManyToMany
    @JoinTable(
            name = "roupa_categoria", joinColumns = @JoinColumn(name = "roupa_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant dataCadastro;

    @OneToMany(mappedBy = "roupa", cascade = CascadeType.ALL)
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
        this.ativo = true;
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

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Integer getQuantidadeTotal() {
        return modelos.stream()
                .reduce(0, (total, modelo) -> total + modelo.getQuantidade(), Integer::sum);
    }

    public Set<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(Set<Modelo> modelos) {
        modelos.forEach(x -> x.setRoupa(this));
        this.modelos = modelos;
    }

    public Set<Tamanho> getTamanhos() {
        return modelos.stream().map(el -> el.getTamanho()).collect(Collectors.toSet());
    }

    public Set<Cor> getCores() {
        return modelos.stream().map(el -> el.getCor()).collect(Collectors.toSet());
    }

    public boolean addModelo(Modelo modelo) {
        boolean adicionado = modelos.add(modelo);
        modelo.setRoupa(this);
        return adicionado;
    }

    public boolean removeModelo(Modelo modelo) {
        boolean removido = modelos.remove(modelo);
        modelo.setRoupa(null);
        return removido;
    }

    public Map<String, List<Modelo>> getModelosPorCores() {
        Map<String, List<Modelo>> listaModelosPorCor = new HashMap<>();

        getCores().forEach(cor -> {
            List<Modelo> modelos = this.modelos.stream()
                    .filter(modelo -> modelo.getCor().equals(cor))
                    .collect(Collectors.toList());

            listaModelosPorCor.put(cor.getNome(), modelos);
        });

        return listaModelosPorCor;
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
