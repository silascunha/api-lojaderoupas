package grupodogrupo.lojaderoupa.model.enums;

public enum MetodoPagamento {

    BOLETO_BANCARIO(0),
    CREDITO(1),
    DEBITO(2);

    private int codigo;

    MetodoPagamento(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static MetodoPagamento valueOf(int codigo) {
        for (MetodoPagamento value : MetodoPagamento.values()) {
            if (codigo == value.getCodigo()) return value;
        }
        throw new IllegalArgumentException("Código invalido do MetodoPagamento");
    }
}
