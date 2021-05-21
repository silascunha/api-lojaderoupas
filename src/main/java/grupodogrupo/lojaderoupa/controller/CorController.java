package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.dto.CreatedResponseDTO;
import grupodogrupo.lojaderoupa.model.Cor;
import grupodogrupo.lojaderoupa.service.CorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/cores")
public class CorController {

    @Autowired
    private CorService corService;

    @GetMapping
    public ResponseEntity<List<Cor>> getAll() {
        List<Cor> lista = corService.getAll();

        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cor> getById(@PathVariable Long id) {
        Cor obj = corService.getById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Cor> insert(@RequestBody Cor obj) {
        obj = corService.insert(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cor> update(@PathVariable Long id, @RequestBody Cor obj) {
        obj = corService.update(id, obj);

        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        corService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/enviarLista")
    public ResponseEntity<List<CreatedResponseDTO>> insertLista(@RequestBody List<Cor> lista) throws MalformedURLException {
        List<CreatedResponseDTO> responseDTOList = new ArrayList<>();

        lista.forEach(el -> {
            Cor obj = corService.insert(el);

            URI uri = ServletUriComponentsBuilder
                    .fromPath("/api/cores")
                    .path("/{id}")
                    .buildAndExpand(obj.getId())
                    .toUri();

            responseDTOList.add(CreatedResponseDTO.build(obj, uri));
        });

        return ResponseEntity.ok().body(responseDTOList);
    }
}
