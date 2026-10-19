package TPO.BATALLA_NAVAL.TDAs;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Barco;

public class FlotaImp implements FlotaInterfaz {

    private Barco[] barcos;
    private int cantidad;

    public FlotaImp() {
        barcos = new Barco[4];
        cantidad = 0;
    }

    @Override
    public boolean agregarBarco(Barco barco) {

        if (estaCompleta()) {
            return false;
        }

        barcos[cantidad] = barco;
        cantidad++;

        return true;
    }

    @Override
    public boolean estaCompleta() {
        return cantidad == barcos.length;
    }

    @Override
    public boolean estaHundida() {

        if (cantidad == 0) {
            return false;
        }

        for (int i = 0; i < cantidad; i++) {

            if (!barcos[i].estaHundido()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int cantidadBarcos() {
        return cantidad;
    }
}
