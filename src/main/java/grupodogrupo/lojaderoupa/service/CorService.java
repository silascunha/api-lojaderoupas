package grupodogrupo.lojaderoupa.service;

import grupodogrupo.lojaderoupa.model.Cor;
import grupodogrupo.lojaderoupa.repository.CorRepository;
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
public class CorService {

    @Autowired
    private CorRepository repository;

    public List<Cor> getAll() {
        List<Cor> cores = repository.findAll();

        return cores;
    }

    public Cor getById(Long id) {
        Optional<Cor> cor = repository.findById(id);

        return cor.orElseThrow(() -> new NotFoundException(id));
    }


    public Cor insert(Cor obj) {
        return repository.save(obj);
    }

    public Cor update(Long id, Cor cor) {
        try {
            Cor obj = repository.getOne(id);

            updateData(obj, cor);

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

    private void updateData(Cor objAntigo, Cor objNovo) {
        objAntigo.setNome(objNovo.getNome());
        objAntigo.setValor(objNovo.getValor());
    }
}
