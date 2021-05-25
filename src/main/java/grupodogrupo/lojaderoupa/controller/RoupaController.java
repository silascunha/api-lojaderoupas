package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.dto.CreatedResponseDTO;
import grupodogrupo.lojaderoupa.model.Modelo;
import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.model.enums.Genero;
import grupodogrupo.lojaderoupa.service.RoupaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/roupas")
public class RoupaController {

    @Autowired
    private RoupaService roupaService;

    @GetMapping
    public ResponseEntity<Page<Roupa>> getAll(Pageable pageable, @RequestParam(value = "ativo", required = false) Boolean ativo) {
        Page<Roupa> roupas = roupaService.getAll(pageable, ativo);

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

    @GetMapping(value = "/filtrar")
    public ResponseEntity<Page<Roupa>> getFiltrados(
                Pageable pageable,
                @RequestParam(value = "filtro_descricao", required = false, defaultValue = "") String descricao,
                @RequestParam(value = "filtro_cor", required = false) List<Long> coresId,
                @RequestParam(value = "filtro_categoria", required = false) List<Long> categoriasId,
                @RequestParam(value = "filtro_genero", required = false) String genero,
                @RequestParam(value = "filtro_tamanho", required = false) List<String> tamanhos,
                @RequestParam(value = "ativo", required = false) Boolean ativo
            ) {

       Page<Roupa> roupas = roupaService.getFiltrados(pageable, descricao, coresId, categoriasId, genero, tamanhos, ativo);

       return ResponseEntity.ok().body(roupas);
    }

    @PostMapping
    public ResponseEntity<CreatedResponseDTO> insert(@RequestBody Roupa obj) throws MalformedURLException {
        Roupa roupa = roupaService.insert(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(roupa.getId())
                .toUri();

        return ResponseEntity.created(uri).body(CreatedResponseDTO.build(roupa, uri));
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

    @GetMapping(value = "/{id}/modelos")
    public ResponseEntity<Set<Modelo>> getModelos(@PathVariable Long id) {
        Roupa obj = roupaService.getById(id);

        return ResponseEntity.ok().body(obj.getModelos());
    }

    @GetMapping(value = "/imagem/{id}/{nome:.+}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> getImagem(@PathVariable Long id, @PathVariable String nome) {
        Resource resource = roupaService.getImage(id, nome);

        return ResponseEntity.ok().body(resource);
    }

    @PostMapping(value = "/enviarImagens/{id}")
    public ResponseEntity<String> enviarImagens(@PathVariable Long id, @RequestParam MultipartFile[] files) {
        roupaService.saveImages(id, Arrays.asList(files));

        return ResponseEntity.ok().body("Imagens enviadas");
    }

    @PostMapping(value = "/enviarLista")
    public ResponseEntity<List<CreatedResponseDTO>> insertLista(@RequestBody List<Roupa> roupas) throws MalformedURLException {
        List<CreatedResponseDTO> responseDTOList = new ArrayList<>();

        roupas.forEach(obj -> {
            Roupa roupa = roupaService.insert(obj);

            URI uri = ServletUriComponentsBuilder
                    .fromPath("/api/roupas")
                    .path("/{id}")
                    .buildAndExpand(roupa.getId())
                    .toUri();

            responseDTOList.add(CreatedResponseDTO.build(roupa, uri));
        });

        return ResponseEntity.ok().body(responseDTOList);
    }

}
