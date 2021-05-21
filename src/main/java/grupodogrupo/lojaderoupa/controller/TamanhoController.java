package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.model.ListaTamanho;
import grupodogrupo.lojaderoupa.model.enums.TipoTamanho;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/tamanhos")
public class TamanhoController {

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getTamanhos() {
        return ResponseEntity.ok().body(ListaTamanho.getTodosTamanhos());
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<List<String>> getTamanhosPorTipo(@PathVariable Integer codigo) {
        TipoTamanho tipo = TipoTamanho.valueOf(codigo);

        return ResponseEntity.ok().body(ListaTamanho.getTamanhos(tipo));
    }
}
