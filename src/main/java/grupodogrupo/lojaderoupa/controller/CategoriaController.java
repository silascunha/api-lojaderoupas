package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.model.Categoria;
import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.repository.CategoriaRepository;
import grupodogrupo.lojaderoupa.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAll() {
        List<Categoria> lista = categoriaService.getAll();

        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id) {
        Categoria obj = categoriaService.getById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/roupas")
    public ResponseEntity<Set<Roupa>> getRoupasDaCategoria(@PathVariable Long id) {
        Set<Roupa> set = categoriaService.getRoupasDaCategoria(id);

        return ResponseEntity.ok().body(set);
    }

    @PostMapping
    public ResponseEntity<Categoria> insert(@RequestBody Categoria obj) {
        obj = categoriaService.insert(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria obj) {
        obj = categoriaService.update(id, obj);

        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
