package grupodogrupo.lojaderoupa.service;

import grupodogrupo.lojaderoupa.model.Imagem;
import grupodogrupo.lojaderoupa.model.Modelo;
import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.model.enums.GeneroRoupa;
import grupodogrupo.lojaderoupa.repository.RoupaRepository;
import grupodogrupo.lojaderoupa.service.exceptions.DatabaseException;
import grupodogrupo.lojaderoupa.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoupaService {

    @Autowired
    private RoupaRepository repository;

    @Autowired
    private FileStorageService storageService;

    public Roupa insert(Roupa roupa) {
        try {
            roupa.setDataCadastro(Instant.now());

            return repository.save(roupa);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new DatabaseException("Não foi possível cadastrar o produto. Erro: " + e.getMessage());
        }
    }

    public Roupa getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Roupa> getAll(Pageable pageable, Boolean ativo) {

        if (ativo == null) {
            return repository.findAll(pageable);
        }
        else {
            return repository.findByAtivo(pageable, ativo);
        }
    }

    public List<Roupa> getRecentes(int limite) {
        List<Roupa> lista = repository.encontreRecentes(limite);

        return lista;
    }

    public Page<Roupa> getFiltrados(
            Pageable pageable,
            String descricao,
            List<Long> coresId,
            List<Long> categoriasId,
            String genero,
            List<String> tamanhos,
            Boolean ativo)
    {
        List<Roupa> roupas;
        if (coresId == null && categoriasId == null && tamanhos == null && genero == null) {
            roupas = repository.findByDescricao(descricao, ativo);
        }
        else {
            GeneroRoupa generoFiltro = (genero != null) ? GeneroRoupa.valueOf(genero) : null;
            roupas = repository.buscarFiltrado(descricao, coresId, categoriasId, generoFiltro, tamanhos, ativo);
        }

        return new PageImpl<>(roupas, pageable, roupas.size());
    }

    public Roupa update(Long id, Roupa roupa) {
        try {
            Roupa obj = repository.getOne(id);

            updateData(obj, roupa);

            return repository.save(obj);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new NotFoundException(id);
        }
    }

    public void delete(Long id) {
        try {
            Roupa obj = repository.getOne(id);
            repository.delete(obj);
            obj.getModelos().forEach(x -> storageService.deleteAllByFolder(x.getId()));
        } catch (EmptyResultDataAccessException | EntityNotFoundException e) {
            e.printStackTrace();
            throw new NotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(Roupa objAntigo, Roupa objNovo) {
        objAntigo.setCategoria(objNovo.getCategoria());
        objAntigo.setDescricao(objNovo.getDescricao());
        objAntigo.setPreco(objNovo.getPreco());
        objAntigo.setAtivo(objNovo.getAtivo());
        objAntigo.setGenero(objNovo.getGenero());

        objAntigo.getModelos().forEach(x -> System.out.println("Modelo obj antigo: "+ x.getId() + " | Roupa id: " + x.getRoupa().getId()));

        Set<Modelo> modelosRemovidos = objAntigo.getModelos()
                .stream().filter(x -> {
                    boolean teste = true;

                    for (Modelo y : objNovo.getModelos()) {
                        if (y.getId() != null && y.getId().equals(x.getId())) {
                            teste = false;
                            break;
                        }
                    }
                    return teste;
                })
                .collect(Collectors.toSet());

        modelosRemovidos.forEach(x -> System.out.println("Modelo do id: "+x.getId()));

        modelosRemovidos.forEach(modelo -> {
            storageService.deleteAllByFolder(modelo.getId());
            objAntigo.removeModelo(modelo);
        });

        objAntigo.setModelos(objNovo.getModelos());

    }

    public Roupa alternarAtivo(Long id) {
        try {
            Roupa obj = repository.getOne(id);

            obj.setAtivo(!obj.getAtivo());

            return repository.save(obj);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw new NotFoundException(id);
        }
    }

    public void saveImages(Long modeloId, List<MultipartFile> files) {
        Roupa obj = repository.findByModelosId(modeloId).orElseThrow(() -> new NotFoundException(modeloId));
        Modelo modelo = obj.getModelos().stream().filter(m -> m.getId().equals(modeloId)).findFirst().get();

        files.forEach(file -> {
            String nome = storageService.save(file, modeloId);
            Imagem imagem = new Imagem();
            imagem.setPath("/api/roupas/imagem/" + modeloId + "/" + nome);
            modelo.getImagens().add(imagem);
        });

        repository.save(obj);
    }

    public Resource getImage(Long modeloId, String nome) {
        return storageService.load(modeloId, nome);
    }
}
