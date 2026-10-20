package TPO.BATALLA_NAVAL.CLASES;

import java.util.Random;

public class Partida {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Random random;

    public Partida(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1;
        this.random = new Random();
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public Jugador getRivalActual() {

        if (jugadorActual == jugador1) {
            return jugador2;
        }

        return jugador1;
    }

    public boolean puedeDisparar(Coordenada coordenada) {
        return !getRivalActual().getTablero().yaRecibioBomba(coordenada);
    }

    public Resultado disparar(Coordenada coordenada)
            throws CasillaYaAtacadaException {

        Jugador rival = getRivalActual();

        if (rival.getTablero().yaRecibioBomba(coordenada)) {
            throw new CasillaYaAtacadaException("Ya se disparo en esa casilla. Elegi otra.");
        }

        Resultado resultado = rival.getTablero().recibirBomba(coordenada);

        if (resultado.getTipo() == Resultado.TRAMPOLIN) {
            rebotar(jugadorActual, rival);
        }

        return resultado;
    }

    public Resultado disparar(int fila, int columna)
            throws CasillaYaAtacadaException {               //Sobregarca de metodos (para swing)
        return disparar(new Coordenada(fila, columna));
    }

    private void rebotar(Jugador tirador, Jugador rival) {

        Jugador destino = tirador;
        Jugador otroTablero = rival;

        while (!termino()) {

            Coordenada coordenada = generarCoordenadaAleatoria(destino.getTablero());
            Resultado resultado = destino.getTablero().recibirBomba(coordenada);

            if (resultado.getTipo() != Resultado.TRAMPOLIN) {
                return;
            }

            Jugador auxiliar = destino;  //Cuando cae en otro trampolin, invierte roles
            destino = otroTablero;
            otroTablero = auxiliar;
        }
    }

    private Coordenada generarCoordenadaAleatoria(Tablero tablero) {

        Coordenada coordenada;

        do {

            int fila = random.nextInt(Tablero.TAMANIO);
            int columna = random.nextInt(Tablero.TAMANIO);

            coordenada = new Coordenada(fila, columna);

        } while (tablero.yaRecibioBomba(coordenada));

        return coordenada;
    }

    public void cambiarTurno() {

        if (jugadorActual == jugador1) {
            jugadorActual = jugador2;
        } else {
            jugadorActual = jugador1;
        }
    }

    public boolean termino() {
        return jugador1.perdio() || jugador2.perdio();
    }

    public Jugador getGanador() {

        if (!termino()) {
            throw new IllegalStateException ("La partida no termino");
        }

        if (jugador1.perdio()) {
            return jugador2;
        }

        return jugador1;
    }
}