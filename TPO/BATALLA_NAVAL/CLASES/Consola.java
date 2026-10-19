package TPO.BATALLA_NAVAL.CLASES;

import java.util.Scanner;

import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Barco;

import java.util.ArrayList;

public class Consola {

    private Scanner scanner;

    public Consola() {
        scanner = new Scanner(System.in);
    }

    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public Coordenada pedirCoordenada(String mensaje) {
        Coordenada coordenada = null;

        while (coordenada == null) {

            System.out.print(mensaje);
            String texto = scanner.nextLine();

            coordenada = Coordenada.desdeTexto(texto);

            if (coordenada == null) {
                System.out.println("Coordenada invalida. Intente nuevamente.");
            }
        }

        return coordenada;
    }

    public boolean pedirOrientacion() {

        while (true) {

            System.out.print("Orientacion (H/V): ");
            String orientacion = scanner.nextLine().trim().toUpperCase();

            if (orientacion.equals("H")) {
                return true;
            }

            if (orientacion.equals("V")) {
                return false;
            }

            System.out.println("Orientacion invalida. Ingrese H o V.");
        }
    }

    public void mostrar(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarLadoALado(String izquierda, String derecha) {

        String[] lineasIzquierda = izquierda.split("\n");
        String[] lineasDerecha = derecha.split("\n");

        int cantidadLineas = lineasIzquierda.length;
        
        for (int i = 0; i < cantidadLineas; i++) {

            String lineaIzquierda = "";
            String lineaDerecha = "";

            if (i < lineasIzquierda.length) {
                lineaIzquierda = lineasIzquierda[i];
            }

            if (i < lineasDerecha.length) {
                lineaDerecha = lineasDerecha[i];
            }

            System.out.printf("%-35s %s%n",
                    lineaIzquierda,
                    lineaDerecha);
        }
    }

    public void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    public void esperarEnter() {
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
    }

    public void mostrarFlota(ArrayList<Barco> barcos) {

    System.out.println("Tu flota:");

    for (Barco barco : barcos) {

        String estado;

        if (barco.estaHundido()) {
            estado = "Hundido";
        } else {
            estado = "Activo";
        }

        System.out.println(
                barco.getNombre()
                + " - Largo " + barco.getLargo()
                + " - " + estado
        );
    }
}


}
