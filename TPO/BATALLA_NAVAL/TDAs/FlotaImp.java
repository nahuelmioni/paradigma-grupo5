package TPO.BATALLA_NAVAL.TDAs;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Barco;

import java.util.ArrayList;
import java.util.Collections;

public class FlotaImp implements FlotaInterfaz {

    private ArrayList<Barco> barcos;

    public FlotaImp() {
        barcos = new ArrayList<Barco>();
    }

    @Override
    public boolean agregarBarco(Barco barco) {

        if (estaCompleta()) {
            return false;
        }

        barcos.add(barco);

        return true;
    }

    @Override
    public boolean estaCompleta() {
        return barcos.size() == 4;
    }

    @Override
    public boolean estaHundida() {

        if (barcos.isEmpty()) {
            return false;
        }

        for (Barco barco : barcos) {

            if (!barco.estaHundido()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int cantidadBarcos() {
        return barcos.size();
    }

    @Override
    public ArrayList<Barco> getBarcosOrdenados() {

        ArrayList<Barco> barcosOrdenados = new ArrayList<Barco>(barcos);

        Collections.sort(barcosOrdenados); //Usa automaticamente el CompareTO de la clase barco.

        return barcosOrdenados;
    }
}
