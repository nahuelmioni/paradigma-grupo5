package TPO.BATALLA_NAVAL;

import TPO.BATALLA_NAVAL.CLASES.*;
import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.*;


public class Main {

    public static void main(String[] args) {

        Consola consola = new Consola();

        String nombre1 = consola.pedirTexto("Nombre del jugador 1: ");
        String nombre2 = consola.pedirTexto("Nombre del jugador 2: ");

        Jugador jugador1 = new Jugador(nombre1);
        Jugador jugador2 = new Jugador(nombre2);

        consola.mostrar("\nPreparacion de " + jugador1.getNombre());
        prepararJugador(consola, jugador1);

        consola.esperarEnter();
        consola.limpiarPantalla();

        consola.mostrar("\nPreparacion de " + jugador2.getNombre());
        prepararJugador(consola, jugador2);

        consola.esperarEnter();
        consola.limpiarPantalla();

        Partida partida = new Partida(jugador1, jugador2);

        while (!partida.termino()) {

            Jugador actual = partida.getJugadorActual();
            Jugador rival = partida.getRivalActual();

            consola.mostrar("Turno de " + actual.getNombre());

            consola.mostrarLadoALado(
                    actual.getTablero().dibujar(true),
                    rival.getTablero().dibujar(false)
            );

            Coordenada disparo = consola.pedirCoordenada("¿Donde tiras la bomba? ");

            while (!partida.puedeDisparar(disparo)) {

                consola.mostrar("Ya se disparo en esa casilla. Elegi otra.");
                disparo = consola.pedirCoordenada("¿Donde tiras la bomba? ");
            }

            Resultado resultado = partida.disparar(disparo);

            consola.mostrar("Resultado: " + resultado);

            if (partida.termino()) {
                break;
            }

            consola.esperarEnter();
            consola.limpiarPantalla();

            partida.cambiarTurno();
        }

        consola.mostrar("Ganador: " + partida.getGanador().getNombre());
    }


    private static void prepararJugador(Consola consola, Jugador jugador) {

        Tablero tablero = jugador.getTablero();

        colocarBarco(
                consola,
                tablero,
                new Barco("Lancha 1", 2)
        );

        colocarBarco(
                consola,
                tablero,
                new Barco("Lancha 2", 2)
        );

        colocarBarco(
                consola,
                tablero,
                new Barco("Fragata", 3)
        );

        colocarBarco(
                consola,
                tablero,
                new Barco("Acorazado", 4)
        );

        for (int i = 1; i <= 4; i++) {  //Ya colocados los barcos, se pasa a colocar los trampolines

            boolean colocado = false;

            while (!colocado) {

                consola.mostrar(tablero.dibujar(true));

                Coordenada coordenada = consola.pedirCoordenada("Posicion del trampolin " + i + ": ");

                colocado = tablero.colocarTrampolin(coordenada);

                if (!colocado) {
                    consola.mostrar("Esa casilla ya esta ocupada.");
                }
            }
        }
    }


    private static void colocarBarco(Consola consola, Tablero tablero, Barco barco) {

        boolean colocado = false;

        while (!colocado) {

            consola.mostrar(tablero.dibujar(true));

            consola.mostrar("Colocando " + barco.getNombre() + " (largo " + barco.getLargo() + ")");

            Coordenada inicio = consola.pedirCoordenada("Casilla inicial: ");

            boolean horizontal = consola.pedirOrientacion();

            colocado = tablero.colocarBarco(
                            barco,
                            inicio,
                            horizontal
                        );

            if (!colocado) {consola.mostrar("No se puede colocar el barco ahi.");
            }
        }
    }
}