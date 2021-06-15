package grupodogrupo.lojaderoupa.model.enums;

public enum StatusPedido {

    PAGAMENTO_PENDENTE(0),
    PAGO(1),
    EM_TRANSPORTE(2),
    ENTREGUE(3),
    CANCELADO(4);

    private int codigo;

    StatusPedido(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusPedido valueOf(int codigo) {
        for (StatusPedido value : StatusPedido.values()) {
            if (codigo == value.getCodigo()) return value;
        }
        throw new IllegalArgumentException("Código invalido do StatusPedido");
    }
}
