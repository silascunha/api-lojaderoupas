package grupodogrupo.lojaderoupa.service.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Object id) {
        super("Recurso não encontrado com o ID: " + id);
    }

}