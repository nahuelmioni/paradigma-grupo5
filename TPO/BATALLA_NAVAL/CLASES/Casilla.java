package TPO.BATALLA_NAVAL.CLASES;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Agua;
import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.ElementoMarino;

public class Casilla {

    private ElementoMarino elemento;
    private boolean recibioBomba;

    public Casilla() {
        this.elemento = new Agua();
        this.recibioBomba = false;
    }

    public boolean estaLibre() {
        return !elemento.ocupaLugar();
    }

    public void ponerElemento(ElementoMarino elemento) {
        this.elemento = elemento;
    }

    public boolean yaRecibioBomba() {
        return recibioBomba;
    }

    public Resultado recibirBomba() {
        recibioBomba = true;
        return elemento.recibirBomba();
    }

    public char getSimbolo(boolean esPropio) {

        if (!esPropio && !recibioBomba) {
            return '~';
        }

        return elemento.getSimbolo(recibioBomba);
    }
}
