package grupodogrupo.lojaderoupa.dto;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;

public class CreatedResponseDTO implements Serializable {

    private String entidade;
    private String path;
    private Object id;

    public static <E> CreatedResponseDTO build(E obj, URI uri) {
        CreatedResponseDTO createdResponseDTO = new CreatedResponseDTO();
        createdResponseDTO.setEntidade(obj.getClass().getSimpleName());
        createdResponseDTO.setPath(uri.getPath());
        try {
            createdResponseDTO.setId(obj.getClass().getDeclaredMethod("getId").invoke(obj));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return createdResponseDTO;
    }

    public String getEntidade() {
        return entidade;
    }

    public String getPath() {
        return path;
    }

    public Object getId() {
        return id;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(Object id) {
        this.id = id;
    }
}
