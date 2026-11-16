package TPO.BATALLA_NAVAL.CLASES;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Agua;
import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.ElementoMarino;

public class Casilla {

    private ElementoMarino elemento;
    private boolean recibioBomba;

    public Casilla() {
        this.elemento = new Agua();  //Arranca en agua por default
        this.recibioBomba = false;   //Al principion no recibió bomba
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
        // GRASP - Polimorfismo: la casilla no pregunta si tiene agua, un barco o un trampolin.
        // Le pide al elemento que reciba la bomba y cada subclase de ElementoMarino responde a su manera.
        return elemento.recibirBomba();
    }

    public char getSimbolo(boolean esPropio) {

        if (!esPropio && !recibioBomba) {  //Si es el campo sival y nunca disparaste ahi solo puede ser un guión
            return '~';
        }

        return elemento.getSimbolo(recibioBomba);
    }
}
