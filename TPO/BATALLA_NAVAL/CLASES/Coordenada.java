package TPO.BATALLA_NAVAL.CLASES;

public class Coordenada {

    private int fila;
    private int columna;

    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public Coordenada(char fila, int columna) {  //Sobrecarga de constructores y uso de this()
        this(fila - 'A', columna - 1);
    }

    public static Coordenada desdeTexto(String texto) {

        if (texto == null) {
            return null;
        }

        texto = texto.trim().toUpperCase();

        if (texto.length() < 2 || texto.length() > 3) {
            return null;
        }

        char letraFila = texto.charAt(0);

        if (letraFila < 'A' || letraFila > 'J') {
            return null;
        }

        int columna;

        try {
            columna = Integer.parseInt(texto.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }

        if (columna < 1 || columna > 10) {
            return null;
        }

        return new Coordenada(letraFila, columna);
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        char letraFila = (char) ('A' + fila);
        return "" + letraFila + (columna + 1);
    }
}
