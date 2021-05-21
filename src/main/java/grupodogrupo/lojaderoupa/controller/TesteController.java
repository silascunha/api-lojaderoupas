package grupodogrupo.lojaderoupa.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/teste")
public class TesteController {

    @GetMapping(value = "/arquivo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> teste() throws MalformedURLException {
        Path root = Paths.get("imagens");
        Path file = root.resolve("5b41f9d2-f77c-47e0-be0b-7e4cb99bbbfb.png");
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok().body(resource);
    }
}
