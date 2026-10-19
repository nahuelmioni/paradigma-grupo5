package TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS;

import TPO.BATALLA_NAVAL.CLASES.Resultado;

public abstract class ElementoMarino {

    private boolean ocupaLugar;

    protected ElementoMarino(boolean ocupaLugar) {
        this.ocupaLugar = ocupaLugar;
    }

    public boolean ocupaLugar() {
        return ocupaLugar;
    }

    public abstract Resultado recibirBomba();

    public abstract char getSimbolo(boolean recibioBomba);
}
