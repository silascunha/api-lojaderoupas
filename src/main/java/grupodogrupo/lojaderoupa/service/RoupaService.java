package grupodogrupo.lojaderoupa.service;

import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.repository.RoupaRepository;
import grupodogrupo.lojaderoupa.service.exceptions.DatabaseException;
import grupodogrupo.lojaderoupa.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;

@Service
public class RoupaService {

    @Autowired
    private RoupaRepository repository;

    public Roupa insert(Roupa roupa) {
        try {
            roupa.setDataCadastro(Instant.now());
            return repository.save(roupa);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new DatabaseException("Possivelmente foram enviadas categorias inexistentes com o produto. Erro: " + e.getMessage());
        }
    }

    public Roupa getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Roupa> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Roupa> getRecentes(int limite) {
        List<Roupa> lista = repository.encontreRecentes(limite);

        return lista;
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
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new NotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(Roupa objAntigo, Roupa objNovo) {
        objAntigo.setCategorias(objNovo.getCategorias());
        objAntigo.setModelos(objNovo.getModelos());
        objAntigo.setDescricao(objNovo.getDescricao());
        objAntigo.setPreco(objNovo.getPreco());
    }
}
