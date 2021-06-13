package grupodogrupo.lojaderoupa.service;

import grupodogrupo.lojaderoupa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioService.getByEmail(email);

        return new User(usuario.getEmail(), usuario.getSenha(), Collections.emptyList());
    }
}
