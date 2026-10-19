package TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS;

import TPO.BATALLA_NAVAL.CLASES.Resultado;

public class Trampolin extends ElementoMarino {

    @Override
    public Resultado recibirBomba() {
        return new Resultado(Resultado.TRAMPOLIN);
    }

    @Override
    public char getSimbolo(boolean recibioBomba) {

        if (recibioBomba) {
            return 'R'; //ROTO
        }

        return 'T';
    }
}
