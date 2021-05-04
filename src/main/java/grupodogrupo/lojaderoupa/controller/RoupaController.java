package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.model.Modelo;
import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.repository.RoupaRepository;
import grupodogrupo.lojaderoupa.service.RoupaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/roupas")
public class RoupaController {

    @Autowired
    private RoupaService roupaService;

    @GetMapping
    public ResponseEntity<Page<Roupa>> getAll(Pageable pageable) {
        Page<Roupa> roupas = roupaService.getAll(pageable);

        return ResponseEntity.ok().body(roupas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Roupa> getById(@PathVariable Long id) {
        Roupa obj = roupaService.getById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/recentes")
    public ResponseEntity<List<Roupa>> getRecentes(@RequestParam(value = "limite", defaultValue = "10") int limite) {
        List<Roupa> lista = roupaService.getRecentes(limite);

        return ResponseEntity.ok().body(lista);
    }

    @PostMapping
    public ResponseEntity<Roupa> insert(@RequestBody Roupa obj) {
        obj = roupaService.insert(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Roupa> update(@PathVariable Long id, @RequestBody Roupa obj) {
        obj = roupaService.update(id, obj);

        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roupaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
