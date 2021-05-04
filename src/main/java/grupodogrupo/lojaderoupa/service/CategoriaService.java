package grupodogrupo.lojaderoupa.service;

import grupodogrupo.lojaderoupa.model.Categoria;
import grupodogrupo.lojaderoupa.model.Roupa;
import grupodogrupo.lojaderoupa.repository.CategoriaRepository;
import grupodogrupo.lojaderoupa.service.exceptions.DatabaseException;
import grupodogrupo.lojaderoupa.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> getAll() {
        List<Categoria> categorias = repository.findAll();

        return categorias;
    }

    public Categoria getById(Long id) {
        Optional<Categoria> categoria = repository.findById(id);

        return categoria.orElseThrow(() -> new NotFoundException(id));
    }

    public Set<Roupa> getRoupasDaCategoria(Long id) {
        Categoria categoria = this.getById(id);

        return categoria.getRoupas();
    }

    public Categoria insert(Categoria obj) {
        return repository.save(obj);
    }

    public Categoria update(Long id, Categoria categoria) {
        try {
            Categoria obj = repository.getOne(id);

            updateData(obj, categoria);

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

    private void updateData(Categoria objAntigo, Categoria objNovo) {
        objAntigo.setNome(objNovo.getNome());
    }
}
