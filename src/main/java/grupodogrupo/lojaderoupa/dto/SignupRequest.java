package grupodogrupo.lojaderoupa.dto;

import grupodogrupo.lojaderoupa.model.Usuario;
import grupodogrupo.lojaderoupa.model.enums.Genero;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;

public class SignupRequest implements Serializable {

    private String nome;

    private String email;

    private String senha;

    private String cpf;

    private String telefone;

    private LocalDate dataNascimento;

    private char genero;

    public SignupRequest() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        if (Genero.has(genero)) {
            this.genero = genero;
        }
    }

    public Usuario toUsuario(PasswordEncoder encoder) {
        Usuario usuario = new Usuario();
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(encoder.encode(senha));
        usuario.setTelefone(telefone);
        usuario.setDataNascimento(dataNascimento);
        usuario.setNome(nome);
        usuario.setGenero(Genero.valueOf(genero));

        return usuario;
    }
}
