package TPO.BATALLA_NAVAL.CLASES;

public class Jugador {

    private String nombre;
    private Tablero tablero;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
    }

    public String getNombre() {
        return nombre;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public boolean perdio() {
        return tablero.flotaHundida();
    }
}