package TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS;

import TPO.BATALLA_NAVAL.CLASES.Resultado;

public abstract class ElementoMarino {

    public abstract Resultado recibirBomba();

    public abstract char getSimbolo(boolean recibioBomba);

    public boolean ocupaLugar() {
        return true;
    }
}
