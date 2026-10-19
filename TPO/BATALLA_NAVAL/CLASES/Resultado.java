package TPO.BATALLA_NAVAL.CLASES;

public class Resultado {

    public static final int AGUA = 0;
    public static final int TOCADO = 1;
    public static final int HUNDIDO = 2;
    public static final int TRAMPOLIN = 3;

    private int tipo;

    public Resultado(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    @Override
    public String toString() {

        switch (tipo) {

            case AGUA:
                return "Agua";

            case TOCADO:
                return "Tocado";

            case HUNDIDO:
                return "Hundido";

            case TRAMPOLIN:
                return "Trampolin";

            default:
                return "Resultado desconocido";
        }
    }
}