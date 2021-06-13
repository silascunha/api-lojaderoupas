package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.model.ListaTamanho;
import grupodogrupo.lojaderoupa.model.enums.TipoTamanho;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/tamanhos")
public class TamanhoController {

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getTamanhos() {
        return ResponseEntity.ok().body(ListaTamanho.getTodosTamanhos());
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<List<String>> getTamanhosPorTipo(@PathVariable String codigo) {
        TipoTamanho tipo = TipoTamanho.valueOf(codigo);

        return ResponseEntity.ok().body(ListaTamanho.getTamanhos(tipo));
    }
}
