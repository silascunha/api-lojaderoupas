package grupodogrupo.lojaderoupa.model;

import grupodogrupo.lojaderoupa.model.enums.TipoTamanho;

import java.io.Serializable;
import java.util.*;

public class ListaTamanho implements Serializable {

    private static final List<String> tamanhosLetra = Arrays.asList("PP", "P", "M", "G", "GG", "EGG");

    private static final List<String> tamanhosNumero = Arrays.asList("36", "38", "40", "42", "44", "46", "48", "50", "52", "54", "56");

    public static List<String> getTamanhos(TipoTamanho tipoTamanho) {
        if (tipoTamanho.equals(TipoTamanho.LETRA)) {
            return tamanhosLetra;
        }
        return tamanhosNumero;
    }

    public static Map<String, List<String>> getTodosTamanhos() {
        Map<String, List<String>> tamanhos = new HashMap<>();

        tamanhos.put("LETRA", tamanhosLetra);
        tamanhos.put("NUMERO", tamanhosNumero);

        return tamanhos;
    }
}
