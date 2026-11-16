package TPO.BATALLA_NAVAL.TDAs;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Barco;

import java.util.ArrayList;
import java.util.Collections;

public class FlotaImp implements FlotaInterfaz { 

    private ArrayList<Barco> barcos;

    public FlotaImp() {
        barcos = new ArrayList<Barco>();  //Creamos la lista vacia de los barcos
    }

    @Override
    public boolean agregarBarco(Barco barco) {

        if (estaCompleta()) {  //Si son 4
            throw new IllegalStateException( "La flota ya esta completa");
        }

        barcos.add(barco);

        return true;
    }

    @Override
    public boolean estaCompleta() {
        return barcos.size() == 4;  //Método propio de ArrayList
    }

    @Override
    public boolean estaHundida() {

        if (barcos.isEmpty()) {  //Método porpio de ArrayList
            return false;
        }

        for (Barco barco : barcos) {  //For each barco en barcos

            if (!barco.estaHundido()) {  //Con que uno no esté hundido alcanza para que la flota no esté hundida
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
