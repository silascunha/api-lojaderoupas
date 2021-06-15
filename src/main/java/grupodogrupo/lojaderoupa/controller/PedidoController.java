package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.dto.CreatedResponseDTO;
import grupodogrupo.lojaderoupa.dto.PedidoDTO;
import grupodogrupo.lojaderoupa.model.Pedido;
import grupodogrupo.lojaderoupa.model.Usuario;
import grupodogrupo.lojaderoupa.service.PedidoService;
import grupodogrupo.lojaderoupa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAll() {
        List<Pedido> lista = pedidoService.getAll();

        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable Long id) {
        Pedido obj = pedidoService.getById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Pedido> insert(@RequestBody PedidoDTO obj) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario user = usuarioService.getByEmail(userDetails.getUsername());

        Pedido pedido = pedidoService.insert(obj, user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedido.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pedido);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido obj) {
        obj = pedidoService.update(id, obj);

        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
