package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.dto.UsuarioDTO;
import grupodogrupo.lojaderoupa.model.Usuario;
import grupodogrupo.lojaderoupa.security.JwtTokenUtil;
import grupodogrupo.lojaderoupa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenUtil jwtUtils;

    @GetMapping(value = "/me")
    public ResponseEntity<UsuarioDTO> getUserData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario user = userService.getByEmail(userDetails.getUsername());

        return ResponseEntity.ok().body(new UsuarioDTO(user));
    }

}
