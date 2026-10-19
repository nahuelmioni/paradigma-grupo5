package TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS;

import TPO.BATALLA_NAVAL.CLASES.Resultado;

public class Barco extends ElementoMarino implements Comparable<Barco> {

    private String nombre;
    private int largo;
    private int impactos;

    public Barco(String nombre, int largo) {
        super(true);
        this.nombre = nombre;
        this.largo = largo;
        this.impactos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLargo() {
        return largo;
    }

    public boolean estaHundido() {
        return impactos >= largo;
    }

    @Override
    public Resultado recibirBomba() {

        impactos++;

        if (estaHundido()) {
            return new Resultado(Resultado.HUNDIDO);
        }

        return new Resultado(Resultado.TOCADO);
    }

    @Override
    public char getSimbolo(boolean recibioBomba) {

        if (recibioBomba) {
            return 'X';
        }

        return 'B';
    }

    @Override
    public int compareTo(Barco otroBarco) {  //Define que la comparacion entre los barcos sea por su largo
        return Integer.compare(otroBarco.largo, this.largo);
    }
}
