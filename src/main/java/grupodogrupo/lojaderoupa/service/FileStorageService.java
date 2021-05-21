package grupodogrupo.lojaderoupa.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    public Path root = Paths.get("imagens");

    public void init() {
        try {
            if (Files.notExists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível criar o diretório raiz de imagens");
        }
    }

    public String save(MultipartFile file, Object modeloId) {
        try {
            Path pathModelo = root.resolve(modeloId.toString());
            if(Files.notExists(pathModelo)) {
                Files.createDirectory(pathModelo);
            }
            System.out.println(file.getOriginalFilename());
            String[] extensao = file.getOriginalFilename().split("\\.");
            System.out.println(extensao[extensao.length -1]);
            String novoNome = UUID.randomUUID().toString() + "." + extensao[extensao.length - 1];
            System.out.println(novoNome);
            Files.copy(file.getInputStream(), pathModelo.resolve(novoNome));
            return novoNome;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível salvar o arquivo!");
        }
    }

    public Resource load(Object modeloId, String nome) {
        try {
            Path file = root.resolve(modeloId.toString() + "/" + nome);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Não foi possível ler o arquivo!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean deleteAllByFolder(Object modeloId) {
        try {
            Path folder = root.resolve(modeloId.toString());

            FileSystemUtils.deleteRecursively(folder);

            return Files.deleteIfExists(folder);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
