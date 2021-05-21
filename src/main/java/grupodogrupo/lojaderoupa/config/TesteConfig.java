package grupodogrupo.lojaderoupa.config;

import grupodogrupo.lojaderoupa.model.*;
import grupodogrupo.lojaderoupa.model.enums.TipoTamanho;
import grupodogrupo.lojaderoupa.repository.CategoriaRepository;
import grupodogrupo.lojaderoupa.repository.CorRepository;
import grupodogrupo.lojaderoupa.repository.RoupaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;

@Configuration
@Profile("teste")
public class TesteConfig implements CommandLineRunner {

    @Autowired
    private RoupaRepository roupaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CorRepository corRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
