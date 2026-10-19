package TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS;

import TPO.BATALLA_NAVAL.CLASES.Resultado;

public class Agua extends ElementoMarino {

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

    @Override
    public boolean ocupaLugar() {
        return false;
    }
}
