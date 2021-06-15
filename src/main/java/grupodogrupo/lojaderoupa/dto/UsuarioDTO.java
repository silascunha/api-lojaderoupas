package grupodogrupo.lojaderoupa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import grupodogrupo.lojaderoupa.model.Usuario;
import grupodogrupo.lojaderoupa.model.enums.Genero;

import java.io.Serializable;
import java.time.LocalDate;

public class UsuarioDTO implements Serializable {

    private Long id;

    private String nome;

    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    private Genero genero;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.dataNascimento = usuario.getDataNascimento();
        this.genero = usuario.getGenero();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }
}
