package grupodogrupo.lojaderoupa.model.enums;

public enum Genero {
    MASCULINO('M'),
    FEMININO('F'),
    OUTRO('O');

    private char codigo;

    Genero(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    public static Genero valueOf(char codigo) {
        for (Genero value : Genero.values()) {
            if (codigo == value.getCodigo()) return value;
        }
        throw new IllegalArgumentException("Código invalido do Gênero");
    }

    public static boolean has(char codigo) {
        for (Genero value : Genero.values()) {
            if (codigo == value.getCodigo()) return true;
        }
        return false;
    }
}
