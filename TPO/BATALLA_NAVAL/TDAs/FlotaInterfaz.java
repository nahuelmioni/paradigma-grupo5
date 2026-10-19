package TPO.BATALLA_NAVAL.TDAs;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Barco;

public interface FlotaInterfaz {

    boolean agregarBarco(Barco barco);

    boolean estaCompleta();

    boolean estaHundida();

    int cantidadBarcos();
}