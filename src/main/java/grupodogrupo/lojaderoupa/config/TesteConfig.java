package grupodogrupo.lojaderoupa.config;

import grupodogrupo.lojaderoupa.model.Modelo;
import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.repository.RoupaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("teste")
public class TesteConfig implements CommandLineRunner {

    @Autowired
    private RoupaRepository roupaRepository;


    @Override
    public void run(String... args) throws Exception {
    }
}
