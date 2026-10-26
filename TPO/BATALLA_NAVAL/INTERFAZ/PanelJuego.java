package TPO.BATALLA_NAVAL.INTERFAZ;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import TPO.BATALLA_NAVAL.CLASES.CasillaYaAtacadaException;
import TPO.BATALLA_NAVAL.CLASES.Coordenada;
import TPO.BATALLA_NAVAL.CLASES.Jugador;
import TPO.BATALLA_NAVAL.CLASES.Partida;
import TPO.BATALLA_NAVAL.CLASES.Resultado;

public class PanelJuego extends JPanel {

    private Partida partida;

    private JLabel etiquetaTurno;
    private JLabel etiquetaResultado;

    private JTextArea tableroPropio;

    private JButton[][] botonesDisparo;

    private String resumenUltimoTurno;

    public PanelJuego(Jugador jugador1, Jugador jugador2) {

        partida = new Partida(jugador1,jugador2);

        resumenUltimoTurno = null; 

        mostrarPantallaEspera();
    }

    private void mostrarPantallaEspera() {

        removeAll();

        setLayout(new BorderLayout());

        Jugador jugador = partida.getJugadorActual();

        String textoMensaje = "<html><div style='text-align:center;'>";

        if (resumenUltimoTurno != null) {

                textoMensaje = textoMensaje + "<br><br><b>Turno anterior:</b><br>" + resumenUltimoTurno.replace("\n", "<br>") + "<br><br>";
        }

        textoMensaje = textoMensaje + "<b>Turno de " + jugador.getNombre() + "</b><br>" + "Presiona cuando estes listo para continuar." + "</div></html>";


        JLabel mensaje = new JLabel(textoMensaje,SwingConstants.CENTER);

        JButton botonListo = new JButton("Estoy listo");

        botonListo.addActionListener(e ->
                mostrarPantallaJuego()
        );

        add(mensaje, BorderLayout.CENTER);
        add(botonListo, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void mostrarPantallaJuego() {

        removeAll();

        setLayout(new BorderLayout());

        crearInterfaz();
        actualizarPantalla();

        revalidate();
        repaint();
    }

    private void crearInterfaz() {

        etiquetaTurno = new JLabel();

        add(
                etiquetaTurno,
                BorderLayout.NORTH
        );

        JPanel panelCentral =
                new JPanel(
                        new GridLayout(
                                1,
                                2, //2 Columnas (tablero propio y tablero rival interactivo en la botonera)
                                10,
                                10
                        )
                );

        crearTableroPropio(panelCentral);
        crearBotoneraDisparo(panelCentral);

        add(panelCentral,BorderLayout.CENTER);

        etiquetaResultado =
                new JLabel(
                        "Esperando disparo..."
                );

        add(
                etiquetaResultado,
                BorderLayout.SOUTH
        );
    }

    private void crearTableroPropio(
            JPanel panelCentral) {

        JPanel panelPropio = new JPanel(new BorderLayout());

        JLabel tituloPropio = new JLabel("Tu tablero", SwingConstants.CENTER);

        tableroPropio = new JTextArea();

        tableroPropio.setEditable(false);

        tableroPropio.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        panelPropio.add(tituloPropio, BorderLayout.NORTH);

        panelPropio.add(new JScrollPane(tableroPropio), BorderLayout.CENTER);

        panelCentral.add(panelPropio);
    }

    private void crearBotoneraDisparo(JPanel panelCentral) {

        JPanel contenedorRival = new JPanel(new BorderLayout()); // Contiene titulo + botonera rival

        JLabel tituloRival = new JLabel("Tablero rival", SwingConstants.CENTER); // Titulo del tablero interactivo

        JPanel panelDisparos = new JPanel(new GridLayout(11,11)); // Grilla de coordenadas y botones

        botonesDisparo = new JButton[10][10];

        panelDisparos.add( new JLabel(""));

        for (int columna = 1; columna <= 10; columna++) {

            JLabel numero = new JLabel(String.valueOf(columna), SwingConstants.CENTER);

            panelDisparos.add(numero);
        }

        for (int fila = 0;
                fila < 10;
                fila++) {

            JLabel letra =
                    new JLabel(
                            String.valueOf(
                                    (char) ('A' + fila)
                            ),
                            SwingConstants.CENTER
                    );

            panelDisparos.add(letra);

            for (int columna = 0; columna < 10; columna++) {

                JButton boton = new JButton();

                botonesDisparo[fila][columna] = boton;

                final int filaSeleccionada =
                        fila;

                final int columnaSeleccionada =
                        columna;

                boton.addActionListener(e ->
                        disparar(filaSeleccionada, columnaSeleccionada)
                );

                panelDisparos.add(boton);
            }
        }

        contenedorRival.add(tituloRival, BorderLayout.NORTH);

        contenedorRival.add(panelDisparos, BorderLayout.CENTER);

        panelCentral.add(contenedorRival);
    }

    private void disparar(int fila, int columna) {

        Coordenada coordenada = new Coordenada(fila, columna);

        Jugador jugadorQueDispara = partida.getJugadorActual(); // Guarda quien hizo el disparo antes de cambiar el turno
                                                                 

        try {

            Resultado resultado =partida.disparar(coordenada);

            resumenUltimoTurno = jugadorQueDispara.getNombre() + " disparo en " + coordenada + ".\nResultado: " + resultado+ "."; // Guarda la informacion que vera el siguiente jugador

            actualizarTableros();

            JOptionPane.showMessageDialog(
                    this,
                    "Resultado: "
                            + resultado
            );

            if (partida.termino()) {

                terminarPartida();

                return;
            }

            partida.cambiarTurno();

            mostrarPantallaEspera();

        } catch (CasillaYaAtacadaException e) {

            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private void actualizarBotoneraRival() {

        Jugador rival = partida.getRivalActual(); // Obtiene el tablero enemigo del jugador actual

        for (int fila = 0; fila < 10; fila++) {

            for (int columna = 0; columna < 10; columna++) {

                Coordenada coordenada =
                        new Coordenada(fila, columna); // Coordenada que estamos representando

                JButton boton =
                        botonesDisparo[fila][columna]; // Boton correspondiente a esa coordenada

                if (rival.getTablero().yaRecibioBomba(coordenada)) {

                    char simbolo =
                            rival.getTablero()
                                    .getSimbolo(coordenada, false); // Consulta el estado real del tablero rival

                    boton.setText(
                            String.valueOf(simbolo)
                    ); // O, X, R, etc.

                    boton.setEnabled(false); // Impide volver a disparar ahi

                } else {

                    boton.setText(""); // Casilla desconocida: no revela contenido rival

                    boton.setEnabled(true); // Todavia puede recibir un disparo
                }
            }
        }
    }

    private void actualizarPantalla() {

        Jugador actual =
                partida.getJugadorActual();

        etiquetaTurno.setText("Turno de: " + actual.getNombre());

        actualizarTableros();
        actualizarBotoneraRival();
    }

    private void actualizarTableros() {

        Jugador actual = partida.getJugadorActual();

        tableroPropio.setText(
                actual
                        .getTablero()
                        .dibujar(true)
        );

    }

    private void terminarPartida() {

        try {

            Jugador ganador =
                    partida.getGanador();

            JOptionPane.showMessageDialog(
                    this,
                    "Ganador: "
                            + ganador.getNombre()
            );

            etiquetaTurno.setText(
                    "Partida finalizada"
            );

            desactivarDisparos();

        } catch (
                IllegalStateException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void desactivarDisparos() {

        for (int fila = 0;
                fila < 10;
                fila++) {

            for (int columna = 0;
                    columna < 10;
                    columna++) {

                botonesDisparo
                        [fila]
                        [columna]
                        .setEnabled(false);
            }
        }
    }
}
