package grupodogrupo.lojaderoupa.model.enums;

public enum TipoTamanho {
    LETRA(0),
    NUMERO(1);

    private int codigo;

    TipoTamanho(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoTamanho valueOf(int codigo) {
        for (TipoTamanho value : TipoTamanho.values()) {
            if (codigo == value.getCodigo()) return value;
        }
        throw new IllegalArgumentException("Código invalido do TipoTamanho");
    }
}
