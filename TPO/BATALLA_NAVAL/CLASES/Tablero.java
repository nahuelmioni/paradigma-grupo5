package TPO.BATALLA_NAVAL.CLASES;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Barco;
import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Trampolin;

public class Tablero {

    public static final int TAMANIO = 10;

    private Casilla[][] casillas;
    private Barco[] flota;
    private int cantidadBarcos;

    public Tablero() {

        casillas = new Casilla[TAMANIO][TAMANIO];
        flota = new Barco[4];
        cantidadBarcos = 0;

        for (int fila = 0; fila < TAMANIO; fila++) {
            for (int columna = 0; columna < TAMANIO; columna++) {
                casillas[fila][columna] = new Casilla();
            }
        }
    }

    public boolean colocarBarco(
            Barco barco,
            Coordenada inicio,
            boolean horizontal) {

        if (cantidadBarcos >= flota.length) {
            return false;
        }

        if (!hayLugar(inicio, barco.getLargo(), horizontal)) {
            return false;
        }

        for (int i = 0; i < barco.getLargo(); i++) {

            int fila = inicio.getFila();
            int columna = inicio.getColumna();

            if (horizontal) {
                columna = columna + i;
            } else {
                fila = fila + i;
            }

            casillas[fila][columna].ponerElemento(barco);
        }

        flota[cantidadBarcos] = barco;
        cantidadBarcos++;

        return true;
    }

    public boolean colocarTrampolin(Coordenada coordenada) {

        int fila = coordenada.getFila();
        int columna = coordenada.getColumna();

        if (!casillas[fila][columna].estaLibre()) {
            return false;
        }

        casillas[fila][columna].ponerElemento(new Trampolin());

        return true;
    }

    private boolean hayLugar(
            Coordenada inicio,
            int largo,
            boolean horizontal) {

        for (int i = 0; i < largo; i++) {

            int fila = inicio.getFila();
            int columna = inicio.getColumna();

            if (horizontal) {
                columna = columna + i;
            } else {
                fila = fila + i;
            }

            if (fila < 0 || fila >= TAMANIO ||
                columna < 0 || columna >= TAMANIO) {

                return false;
            }

            if (!casillas[fila][columna].estaLibre()) {
                return false;
            }
        }

        return true;
    }

    public boolean yaRecibioBomba(Coordenada coordenada) {

        return casillas[coordenada.getFila()][coordenada.getColumna()].yaRecibioBomba();
    }

    public Resultado recibirBomba(Coordenada coordenada) {

        return casillas[coordenada.getFila()][coordenada.getColumna()].recibirBomba();
    }

    public boolean flotaHundida() {

        if (cantidadBarcos == 0) {
            return false;
        }

        for (int i = 0; i < cantidadBarcos; i++) {

            if (!flota[i].estaHundido()) {
                return false;
            }
        }

        return true;
    }

    public String dibujar(boolean esPropio) {

        String dibujo = "   1 2 3 4 5 6 7 8 9 10\n";

        for (int fila = 0; fila < TAMANIO; fila++) {

            char letraFila = (char) ('A' + fila);

            dibujo = dibujo + letraFila + "  ";

            for (int columna = 0; columna < TAMANIO; columna++) {

                dibujo = dibujo + casillas[fila][columna].getSimbolo(esPropio) + " ";
            }

            dibujo = dibujo + "\n";
        }

        return dibujo;
    }
}