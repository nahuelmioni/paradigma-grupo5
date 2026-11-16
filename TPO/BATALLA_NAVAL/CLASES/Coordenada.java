package TPO.BATALLA_NAVAL.CLASES;

public class Coordenada {

    private int fila;
    private int columna;

    public Coordenada(int fila, int columna) {  //Recibe índices ya listos (0..9). Es el que usan PanelPreparacion, PanelJuego y Partida.generarCoordenadaAleatoria()
        this.fila = fila;
        this.columna = columna;
    }
    //Sobrecarga de constructores y uso de this()
    public Coordenada(char fila, int columna) { 
        this(fila - 'A', columna - 1);   //Lo pasa a (int int) y con this llama al otro constructor con esos datos traducidos
    }

    public static Coordenada desdeTexto(String texto) {

        if (texto == null) {
            return null;
        }

        texto = texto.trim().toUpperCase();

        if (texto.length() < 2 || texto.length() > 3) {  //o de 2 o de 3 caracteres
            return null;
        }

        char letraFila = texto.charAt(0);  //Agarra el primer caracter (0)

        if (letraFila < 'A' || letraFila > 'J') {  //Chequea que esté en el rango
            return null;
        }

        Integer columna;

        try {
            columna = Integer.valueOf(texto.substring(1));  //valueOf devuelve un Integer a partir de un texto. substring(1) corta el texto desde la posicion 1 en adelante
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
        return "" + letraFila + (columna + 1);  //Pasamos de (0, 0) a (A, 1) por ejemplo
    }
}
