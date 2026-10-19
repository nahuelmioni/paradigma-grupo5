package TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS;

import TPO.BATALLA_NAVAL.CLASES.Resultado;

public class Agua extends ElementoMarino {

    public Agua() {
        super(false);
    }

    @Override
    public Resultado recibirBomba() {
        return new Resultado(Resultado.AGUA);
    }

    @Override
    public char getSimbolo(boolean recibioBomba) {

        if (recibioBomba) {
            return 'O';
        }

        return '~';
    }
}
