package TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS;

import TPO.BATALLA_NAVAL.CLASES.Resultado;

public abstract class ElementoMarino { //Padre que define contrato polimórfico

    private boolean ocupaLugar;

    protected ElementoMarino(boolean ocupaLugar) { //Protected porque solo sus clases hijas lo van a llamar usando super(...)
        this.ocupaLugar = ocupaLugar;
    }

    public boolean ocupaLugar() {
        return ocupaLugar;
    }

    public abstract Resultado recibirBomba();

    public abstract char getSimbolo(boolean recibioBomba);
}
